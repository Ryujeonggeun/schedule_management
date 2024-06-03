package com.sparta.schedule_management.jwt;

import com.sparta.schedule_management.entity.UserRoleEnum;
import com.sparta.schedule_management.security.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        log.info("doFilterInternal 실행");
        //AccessToken, refreshToken 가져오기
        String accessToken = jwtUtil.getAccessTokenFromRequest(req);
        String refreshToken = jwtUtil.getRefreshTokenFromRequest(req);


        //만료된 토큰인지 검사
        boolean accessTokenExpiration = checkAccessToken(accessToken);

        //만료된 토큰은 재발급
        if (accessTokenExpiration) {

            //리 프레시 토큰이 없으면 종료
            if (!StringUtils.hasText(refreshToken)) {
                log.error("No valid Access or Refresh Token");
                return;
            }

            //리 프레시 토큰이 유효하지 않으면 종료
            refreshToken = jwtUtil.substringToken(refreshToken);
            if (!jwtUtil.validateToken(refreshToken)) {
                log.error("Refresh Token Error or missing");
                return;
            }


            log.info("Refresh Token valid, 새 AccessToken 발급");
            // Refresh 토큰에서 사용자 정보 추출
            Claims refreshClaims = jwtUtil.getUserInfoFromToken(refreshToken);
            String username = jwtUtil.getUsernameFromToken(refreshToken);
            UserRoleEnum role = refreshClaims.get("role", UserRoleEnum.class);

            // 새로운 Access 토큰 발급
            String newAccessToken = jwtUtil.generateToken(username, role, jwtUtil.ACCESS_TOKEN_EXPIRATION, "access");
            log.info("newAccessToken= " + newAccessToken);
            jwtUtil.addJwtToCookie(res, newAccessToken, jwtUtil.ACCESS_TOKEN_HEADER);

            // 발급한 새로운 Access 토큰으로 인증 설정
            try {
                setAuthentication(username);
            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }
        } else {
            // Access 토큰이 유효한 경우 사용자 정보 추출 후 인증 설정
            Claims accessClaims = jwtUtil.getUserInfoFromToken(accessToken);

            try {
                setAuthentication(accessClaims.getSubject());
            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }
        }
        filterChain.doFilter(req, res);
    }

    private boolean checkAccessToken(String accessToken) {
        // hasText : Null체크
        if (!StringUtils.hasText(accessToken)) {
            return false;
        }

        // JWT 토큰 substring
        accessToken = jwtUtil.substringToken(accessToken);
        log.info("AccessToken= " + accessToken);

        // Access 토큰 유효성 검사
        try {
            return jwtUtil.validateToken(accessToken);
        } catch (Exception e) {
            log.error("Access Token Error");

        }
        return false;
    }

    // 인증 처리
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
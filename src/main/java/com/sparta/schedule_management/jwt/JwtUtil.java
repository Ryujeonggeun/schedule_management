package com.sparta.schedule_management.jwt;

import com.sparta.schedule_management.entity.UserRoleEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component

public class JwtUtil {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    // 사용자 권한 값의 KEY
    public static final String AUTHORIZATION_KEY = "auth";
    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";

    public static final String ACCESS_TOKEN_HEADER = "ACCESS_TOKEN_HEADER";
    public static final String REFRESH_TOKEN_HEADER = "REFRESH_TOKEN_HEADER";

    // Access Token 만료시간 설정 (30초)
    public final long ACCESS_TOKEN_EXPIRATION = 10 * 1000L; // 30분
    // Refresh Token 만료기간 설정(1분)
    public final long REFRESH_TOKEN_EXPIRATION = 60 * 60 * 1000L; //1시간

    // 로그 설정
    public static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그");

    @Value("${jwt.secret}")
    private String secret;
    private Key key;

    //    생성자가 만들어진후 한번만 실행됨
    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secret);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // JWT Access 토큰 , Refresh 토큰 생성
    public String generateToken(String username, UserRoleEnum role, Long expires, String tokenType) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expires);

        return BEARER_PREFIX + Jwts.builder()
                .setSubject(username)
                .claim(AUTHORIZATION_HEADER, role)//사용자 권한
                .claim("tokenType", tokenType)// 토큰 타입 추가
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }


    //사용자에서 AccessToken 가져오기
    public String getAccessTokenFromRequest(HttpServletRequest req) {
        return getTokenFromRequest(req, ACCESS_TOKEN_HEADER);
    }
    //사용자에서 RefreshToken 가져오기
    public String getRefreshTokenFromRequest(HttpServletRequest req) {
        return getTokenFromRequest(req, REFRESH_TOKEN_HEADER);
    }

    // HttpServletRequest 에서 Cookie Value : JWT 가져오기
    public String getTokenFromRequest(HttpServletRequest req, String headerName) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(headerName)) {
                    try {
                        return URLDecoder.decode(cookie.getValue(), "UTF-8"); // Encode 되어 넘어간 Value 다시 Decode
                    } catch (UnsupportedEncodingException e) {
                        return null;
                    }
                }
            }
        }
        return null;
    }

    // JWT 토큰 substring
    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            // bearer 이 공백까지 합쳐서 7자이기 때문에 이거를 잘라냄
            // 이 자른 코드를 밑의 JWT 검증에서 검증함
            return tokenValue.substring(7);
        }
        logger.error("Not Found Token");
        throw new NullPointerException("Not Found Token");
    }





    public void addJwtToCookie(HttpServletResponse res, String token,String headerName) {

        try {
            token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행

            Cookie cookie = new Cookie(headerName, token); // Name-Value
            cookie.setPath("/");

            // Response 객체에 Cookie 추가
            res.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }


    // JWT 토큰에서 사용자 이름 추출
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            // 토큰에 위변조가 있는지, 만료되지는 않았는지 검증
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            logger.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token, 만료된 JWT token 입니다.");
            return false;
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }


}
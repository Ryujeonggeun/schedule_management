package com.sparta.schedule_management;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.schedule_management.controller.CommentController;
import com.sparta.schedule_management.dto.CommentRequestDto;
import com.sparta.schedule_management.dto.CommentResponseDto;
import com.sparta.schedule_management.entity.Comment;
import com.sparta.schedule_management.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CommentService commentService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        // 테스트를 위한 setup 코드 작성
    }

    @Test
    public void testCreateComment() throws Exception {
        // given
        CommentRequestDto requestDto = new CommentRequestDto();
        requestDto.setContent("Test comment");

        // when
        when(commentService.createComment(requestDto)).thenReturn(new CommentResponseDto(new Comment()));

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
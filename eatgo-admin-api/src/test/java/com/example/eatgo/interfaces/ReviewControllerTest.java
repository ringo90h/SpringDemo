package com.example.eatgo.interfaces;

import com.example.eatgo.applicaion.MenuItemService;
import com.example.eatgo.applicaion.ReviewService;
import com.example.eatgo.domain.Review;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReviewService reviewService;


    @Test
    public void createWithValidAttributes() throws Exception {
        given(reviewService.addReview(eq(1L),any())).willReturn(
                Review.builder()
                        .id(123L)
                        .name("JOKER")
                        .score(3)
                        .description("Mat-it-da")
                        .build());

        mvc.perform(post("/restourants/1/reviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"JOKER\",\"score\":3,\"description\":\"Mat-it-da\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/restourants/1/reviews/123"));

        verify(reviewService).addReview(eq(1L),any());
    }

    @Test
    public void createWithInvalidAttributes() throws Exception {
        mvc.perform(post("/restourants/1/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());

        verify(reviewService, never()).addReview(eq(1L),any());
    }

}
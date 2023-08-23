package dbDive.airbnbClone.api.review.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dbDive.airbnbClone.api.review.dto.request.ReviewRequest;
import dbDive.airbnbClone.entity.accommodation.Accommodation;
import dbDive.airbnbClone.entity.review.Review;
import dbDive.airbnbClone.entity.user.User;
import dbDive.airbnbClone.repository.accommodation.AccommodationRepository;
import dbDive.airbnbClone.repository.review.ReviewRepository;
import dbDive.airbnbClone.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReviewControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired AccommodationRepository acmdRepository;
    @Autowired UserRepository userRepository;
    @Autowired ReviewRepository reviewRepository;
    @Autowired ObjectMapper objectMapper;

    @Test
    void 리뷰_조회() throws Exception {
        mockMvc.perform(get("/api/reviews/2?page=1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 리뷰_등록() throws Exception {
        ReviewRequest request = new ReviewRequest("하록 최고", 5);
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/auth/user/accommodation/review/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 리뷰_수정() throws Exception {
        ReviewRequest request = new ReviewRequest("하록 최고");
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/api/auth/user/accommodation/review/1/51")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 리뷰_삭제() throws Exception {
        mockMvc.perform(delete("/api/auth/user/accommodation/review/100/46"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}

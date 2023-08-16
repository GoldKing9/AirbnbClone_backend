package dbDive.airbnbClone.api.review.controller;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReviewControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired AccommodationRepository acmdRepository;
    @Autowired UserRepository userRepository;
    @Autowired ReviewRepository reviewRepository;

    @Test
    void 리뷰_조회() throws Exception {
        User user = new User();
        userRepository.save(user);
        Accommodation acmd = new Accommodation();
        acmdRepository.save(acmd);
        Review review = Review.builder().comment("짱이네요").user(user).accommodation(acmd).build();
        Review review2 = Review.builder().comment("최고예요").user(user).accommodation(acmd).build();
        reviewRepository.save(review);
        reviewRepository.save(review2);


        mockMvc.perform(get("/api/reviews/1?page=2&size=1"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
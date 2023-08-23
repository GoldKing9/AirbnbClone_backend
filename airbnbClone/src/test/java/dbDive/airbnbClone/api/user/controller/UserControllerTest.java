package dbDive.airbnbClone.api.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dbDive.airbnbClone.api.user.dto.request.ModifyUserProfileRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired MockMvc mockMvc;

    @Test
    void 회원_리뷰_조회() throws Exception {
        mockMvc.perform(get("/api/user/6/review?page=1&size=3"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 회원_정보_수정() throws Exception {
        ModifyUserProfileRequest request = new ModifyUserProfileRequest("하록 최고");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/api/auth/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 회원_조회() throws Exception {
        mockMvc.perform(get("/api/user/4"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}

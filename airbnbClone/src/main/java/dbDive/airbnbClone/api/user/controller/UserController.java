package dbDive.airbnbClone.api.user.controller;

import dbDive.airbnbClone.api.user.dto.request.ModifyUserProfileRequest;
import dbDive.airbnbClone.api.user.dto.request.SignupReq;
import dbDive.airbnbClone.api.user.dto.response.UserProfileResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import dbDive.airbnbClone.api.user.dto.response.UserReviewResponse;
import dbDive.airbnbClone.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/user/signup")
    public ResponseEntity<String> signup(@RequestBody SignupReq signupReq) {
        userService.signup(signupReq);

        return ResponseEntity.ok().body("회원가입 성공");
    }

    @GetMapping("/api/user/{userId}/review")
    public UserReviewResponse userReview(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page-1, size);

        return userService.getUserReviews(userId, pageable);
    }

    @PutMapping("/api/auth/user/{userId}")
    public void modifyUserProfile(@PathVariable Long userId, @RequestBody ModifyUserProfileRequest request){
        userService.modifyUserProfile(userId, request);
    }

    @GetMapping("/api/user/{userId}")
    public UserProfileResponse userProfile(@PathVariable Long userId){
        return userService.getUserProfile(userId);
    }
}

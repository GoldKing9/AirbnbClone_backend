package dbDive.airbnbClone.api.user.controller;

import dbDive.airbnbClone.api.user.dto.request.LoginReq;
import dbDive.airbnbClone.api.user.dto.request.ModifyUserProfileRequest;
import dbDive.airbnbClone.api.user.dto.request.SignupReq;
import dbDive.airbnbClone.api.user.dto.response.UserProfileResponse;
import dbDive.airbnbClone.config.auth.AuthUser;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import dbDive.airbnbClone.api.user.dto.response.UserReviewResponse;
import dbDive.airbnbClone.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/user/signup")
    public void signup(@Validated @RequestBody SignupReq signupReq) {
        userService.signup(signupReq);
    }

    @PostMapping("/api/user/login")
    public void login(@Validated @RequestBody LoginReq loginReq, HttpServletResponse response) {

        response.setHeader("Authorization", userService.login(loginReq));
    }

    @PostMapping("/api/auth/user/logout")
    public void logout(@AuthenticationPrincipal AuthUser authUser) {
        
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

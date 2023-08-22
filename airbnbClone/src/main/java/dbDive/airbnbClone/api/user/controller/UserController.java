package dbDive.airbnbClone.api.user.controller;

import dbDive.airbnbClone.api.user.dto.request.LoginRequest;
import dbDive.airbnbClone.api.user.dto.request.ModifyUserProfileRequest;
import dbDive.airbnbClone.api.user.dto.request.SignupRequest;
import dbDive.airbnbClone.api.user.dto.response.UserProfileResponse;
import dbDive.airbnbClone.api.user.dto.response.UserReviewResponse;
import dbDive.airbnbClone.api.user.service.UserService;
import dbDive.airbnbClone.config.auth.AuthUser;
import dbDive.airbnbClone.config.utils.JwtProperties;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static dbDive.airbnbClone.config.utils.JwtProperties.HEADER_STRING;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/user/signup")
    public void signup(@Validated @RequestBody SignupRequest signupRequest) {
        userService.signup(signupRequest);
    }

    @PostMapping("/api/user/login")
    public void login(@Validated @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        response.setHeader(HEADER_STRING, userService.login(loginRequest));
    }

    @PostMapping("/api/auth/user/logout")
    public void logout(@AuthenticationPrincipal AuthUser authUser) {

    }

    @GetMapping("/api/user/{userId}/review")
    public UserReviewResponse userReview(
            @PathVariable Long userId,
            Pageable pageable
    ) {
        return userService.getUserReviews(userId, pageable);
    }

    @PutMapping("/api/auth/user")
    public void modifyUserProfile(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestBody ModifyUserProfileRequest request) {

        userService.modifyUserProfile(authUser.getUser(), request);
    }

    @GetMapping("/api/user/{userId}")
    public UserProfileResponse userProfile(@PathVariable Long userId) {
        return userService.getUserProfile(userId);
    }
}

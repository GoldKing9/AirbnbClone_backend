package dbDive.airbnbClone.api.user.controller;

import dbDive.airbnbClone.api.user.dto.request.SignupReq;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import dbDive.airbnbClone.api.user.dto.response.UserReviewResponse;
import dbDive.airbnbClone.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupReq signupReq) {
        userService.signup(signupReq);

        return ResponseEntity.ok().body("회원가입 성공");
    }

    @GetMapping("/{userId}/review")
    public UserReviewResponse userReview(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page-1, size);

        return userService.getUserReviews(userId, pageable);
    }
}

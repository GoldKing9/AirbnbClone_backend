package dbDive.airbnbClone.api.user.controller;

import dbDive.airbnbClone.api.user.dto.request.SignupReq;
import dbDive.airbnbClone.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
}

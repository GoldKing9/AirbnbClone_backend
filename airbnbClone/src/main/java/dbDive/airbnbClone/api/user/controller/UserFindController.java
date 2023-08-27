package dbDive.airbnbClone.api.user.controller;

import dbDive.airbnbClone.api.user.dto.request.ChangePasswordRequest;
import dbDive.airbnbClone.api.user.dto.request.FindEmailRequest;
import dbDive.airbnbClone.api.user.dto.request.FindPasswordRequest;
import dbDive.airbnbClone.api.user.service.UserFindService;
import dbDive.airbnbClone.config.auth.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserFindController {
    private final UserFindService userFindService;

    @PostMapping("/email")
    @ResponseStatus(code= HttpStatus.OK)
    public void findEmail(@RequestBody FindEmailRequest findEmailRequest) {
        userFindService.findEmail(findEmailRequest);
    }

    @PostMapping("/password")
    @ResponseStatus(code= HttpStatus.OK)
    public void findPassword(@RequestBody FindPasswordRequest findPasswordRequest) {
        userFindService.findPassword(findPasswordRequest);
    }

    @PostMapping("/changePassword")
    @ResponseStatus(code= HttpStatus.OK)
    public void changePassword(@RequestBody ChangePasswordRequest changePasswordRequest, @AuthenticationPrincipal AuthUser authUser) {
        userFindService.changePassword(changePasswordRequest, authUser);
    }
}
package dbDive.airbnbClone.api.user.service;

import dbDive.airbnbClone.api.user.dto.request.LoginReq;
import dbDive.airbnbClone.api.user.dto.request.ModifyUserProfileRequest;
import dbDive.airbnbClone.api.user.dto.request.SignupReq;
import dbDive.airbnbClone.api.user.dto.response.UserProfileResponse;
import dbDive.airbnbClone.common.GlobalException;
import dbDive.airbnbClone.config.auth.AuthUser;
import dbDive.airbnbClone.config.utils.JwtProperties;
import dbDive.airbnbClone.config.utils.JwtUtils;
import dbDive.airbnbClone.entity.user.User;
import dbDive.airbnbClone.entity.user.UserRole;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import dbDive.airbnbClone.api.user.dto.response.UserReviewResponse;
import dbDive.airbnbClone.api.user.dto.response.UserReviews;
import dbDive.airbnbClone.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public void signup(SignupReq signupReq) {

        userRepository.findByEmail(signupReq.getEmail())
                .ifPresent(user -> {throw new GlobalException("중복된 유저가 존재합니다.");});

        User newUser = User.builder()
                .email(signupReq.getEmail())
                .password(passwordEncoder.encode(signupReq.getPassword()))
                .role(UserRole.USER)
                .username(signupReq.getUsername())
                .birth(signupReq.getBirth())
                .userDescription("")
                .build();

        userRepository.save(newUser);

    }

    public String login(LoginReq loginReq) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        AuthUser authUser = (AuthUser) authentication.getPrincipal();

        String jwt = JwtProperties.TOKEN_PREFIX + jwtUtils.generateJwt(authUser);

        return jwt;

    }

    public UserReviewResponse getUserReviews(Long userId, Pageable pageable) {
        PageImpl<UserReviews> allByUserId = userRepository.findAllByUserId(userId, pageable);

        return new UserReviewResponse(allByUserId);
    }

    @Transactional
    public void modifyUserProfile(Long userId, ModifyUserProfileRequest request) {
        // TODO : 로그인 유저 ID와 userID 같은지 검증

        User user = userRepository.findById(userId).orElseThrow(() -> new GlobalException("존재하지 않는 회원입니다."));
        user.update(request.getUserDescription());
    }

    public UserProfileResponse getUserProfile(Long userId) {
        return userRepository.getUserProfile(userId);
    }
}

package dbDive.airbnbClone.api.user.service;

import dbDive.airbnbClone.api.user.dto.request.LoginRequest;
import dbDive.airbnbClone.api.user.dto.request.ModifyUserProfileRequest;
import dbDive.airbnbClone.api.user.dto.request.SignupRequest;
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

import static dbDive.airbnbClone.config.utils.JwtProperties.TOKEN_PREFIX;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Transactional
    public void signup(SignupRequest signupRequest) {

        userRepository.findByEmail(signupRequest.getEmail())
                .ifPresent(user -> {throw new GlobalException("중복된 유저가 존재합니다.");});

        User newUser = User.builder()
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .role(UserRole.USER)
                .username(signupRequest.getUsername())
                .birth(signupRequest.getBirth())
                .userDescription("")
                .build();

        userRepository.save(newUser);

    }

    @Transactional
    public String login(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        AuthUser authUser = (AuthUser) authentication.getPrincipal();

        return TOKEN_PREFIX + jwtUtils.generateJwt(authUser);

    }

    public UserReviewResponse getUserReviews(Long userId, Pageable pageable) {
        PageImpl<UserReviews> allByUserId = userRepository.findAllByUserId(userId, pageable);

        return new UserReviewResponse(allByUserId);
    }

    @Transactional
    public void modifyUserProfile(User user, ModifyUserProfileRequest request) {

        user.update(request.getUserDescription());
    }

    public UserProfileResponse getUserProfile(Long userId) {
        return userRepository.getUserProfile(userId);
    }
}

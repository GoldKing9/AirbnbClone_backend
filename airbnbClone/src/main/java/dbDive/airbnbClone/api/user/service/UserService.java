package dbDive.airbnbClone.api.user.service;

import dbDive.airbnbClone.api.user.dto.request.SignupReq;
import dbDive.airbnbClone.common.GlobalException;
import dbDive.airbnbClone.entity.user.User;
import dbDive.airbnbClone.entity.user.UserRole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import dbDive.airbnbClone.api.user.dto.response.UserReviewResponse;
import dbDive.airbnbClone.api.user.dto.response.UserReviews;
import dbDive.airbnbClone.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
  
    public void signup(SignupReq signupReq) {

        userRepository.findByEmail(signupReq.getEmail()).ifPresent(user -> {
            throw new GlobalException("이미 존재하는 유저");
        });

        User newUser = User.builder()
                .username(signupReq.getUsername())
                .email(signupReq.getEmail())
                .password(passwordEncoder.encode(signupReq.getPassword()))
                .birth(signupReq.getBirth())
                .role(UserRole.USER)
                .build();

        userRepository.save(newUser);
    }


    public UserReviewResponse getUserReviews(Long userId, Pageable pageable) {
        PageImpl<UserReviews> allByUserId = userRepository.findAllByUserId(userId, pageable);

        return new UserReviewResponse(allByUserId);
    }
}

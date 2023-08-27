package dbDive.airbnbClone.api.user.service;

import dbDive.airbnbClone.api.user.dto.request.ChangePasswordRequest;
import dbDive.airbnbClone.api.user.dto.request.FindEmailRequest;
import dbDive.airbnbClone.api.user.dto.request.FindPasswordRequest;
import dbDive.airbnbClone.common.GlobalException;
import dbDive.airbnbClone.config.auth.AuthUser;
import dbDive.airbnbClone.entity.user.User;
import dbDive.airbnbClone.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserFindService {

    @Value("${spring.mail.username}")
    private String sendEmail;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;
    ;

    public void findEmail(FindEmailRequest findEmailRequest) {
        String username = findEmailRequest.getUsername();
        LocalDate userBirth = findEmailRequest.getBirth();
        String findEmail = userRepository.findByUsernameAndBirth(username, userBirth).getEmail();

        try {
            if (findEmail != null) {
                String email = findEmail;
                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                simpleMailMessage.setTo(email);
                simpleMailMessage.setSubject("[Airbnb] 이메일 찾기");
                simpleMailMessage.setText("가입하신 이메일은 " + email + "입니다.");
                simpleMailMessage.setFrom(sendEmail);
                mailSender.send(simpleMailMessage);
            } else {
                throw new Exception("이메일이 존재하지 않습니다.");
            }
        } catch (Exception e) {
            // 예외 발생 시 처리 로직
            e.printStackTrace(); // 혹은 로깅 등을 수행
        }
    }

    public void findPassword(FindPasswordRequest findPasswordRequest) {
        String userEmail = findPasswordRequest.getEmail();
        User user = userRepository.findByEmail(userEmail).orElseThrow(()
                -> new GlobalException("사용자가 존재하지 않습니다."));

        String tempPassword = getTempPassword();
        user.passwordUpdate(passwordEncoder.encode(tempPassword));
        userRepository.save(user);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(userEmail);
        simpleMailMessage.setSubject("임시 비밀번호 안내 이메일 입니다.");
        simpleMailMessage.setText("안녕하세요. 임시 비밀번호 안내 관련 이메일 입니다.\n"
                + "회원님의 임시 비밀번호는 "
                + tempPassword
                + " 입니다. \n"
                + "로그인 후 비밀 변호를 변경해주세요.");
        simpleMailMessage.setFrom(sendEmail);
        mailSender.send(simpleMailMessage);

        System.out.println("비밀번호 찾기 성공");

    }

    public void changePassword(ChangePasswordRequest changePasswordRequest, AuthUser authUser) {
        User user = userRepository.findByEmail(authUser.getUser().getEmail()).orElseThrow();
        String newPassword = changePasswordRequest.getNewPassword();
        String currentPassword = changePasswordRequest.getCurrentPassword();
        String registeredPassword = user.getPassword();

        if (passwordEncoder.matches(currentPassword, registeredPassword)) {
            user.passwordUpdate(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } else {
            throw new GlobalException("현재 비밀번호가 일치하지 않습니다");
        }
        System.out.println("비밀번호 변경완료");
    }

    public String getTempPassword() {
        char[] charSet = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder strBuilder = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int idx = random.nextInt(charSet.length);
            strBuilder.append(charSet[idx]);
        }
        return strBuilder.toString();
    }
}

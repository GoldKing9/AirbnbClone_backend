package dbDive.airbnbClone.config.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dbDive.airbnbClone.common.GlobalException;
import dbDive.airbnbClone.config.auth.AuthUser;
import dbDive.airbnbClone.entity.user.User;
import dbDive.airbnbClone.entity.user.UserRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtils {
    private static Key key;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static String generateJwt(AuthUser authUser) {
        byte[] keyBytes = Decoders.BASE64.decode(JwtProperties.secretKey);
        key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                .claim("id", authUser.getUser().getId())
                .claim("email", authUser.getUser().getEmail())
                .claim("username", authUser.getUser().getUsername())
                .claim("role", authUser.getUser().getRole())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JwtProperties.ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    public String parseJwt(HttpServletRequest request) {
        String jwt = request.getHeader(JwtProperties.HEADER_STRING);

        if (StringUtils.hasText(jwt) && jwt.startsWith(JwtProperties.TOKEN_PREFIX)) {
            jwt = jwt.replace(JwtProperties.TOKEN_PREFIX, "");
        }

        return jwt;
    }

    public boolean validationJwt(String token, HttpServletResponse response) throws IOException {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token); //토큰에 대한 검증 로직
            return true;
        } catch (ExpiredJwtException e) {
            setResponse(response, e, "토큰시간이 만료되었습니다.");
        } catch (MalformedJwtException e) {
            setResponse(response, e, "JWT 토큰이 유효하지 않습니다.");
        } catch (UnsupportedJwtException e) {
            setResponse(response, e, "지원하지 않는 토큰입니다.");
        }
        return false;
    }

    private void setResponse(HttpServletResponse response, Exception e, String errorMessage) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(objectMapper.writeValueAsString(new GlobalException(errorMessage)));
    }

    public AuthUser getAuthUser(String jwt) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        Long id = claims.get("id", Long.class);
        String email = claims.get("email", String.class);
        String username = claims.get("username", String.class);
        String role = claims.get("role", String.class);

        User user = User.builder()
                .id(id)
                .email(email)
                .username(username)
                .role(UserRole.valueOf(role))
                .build();

        return new AuthUser(user);
    }
}

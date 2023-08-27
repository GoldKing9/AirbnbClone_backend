package dbDive.airbnbClone.repository.user;

import dbDive.airbnbClone.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    Optional<User> findByEmail(String email);
    User findByUsernameAndBirth(String username, LocalDate userBirth);
}
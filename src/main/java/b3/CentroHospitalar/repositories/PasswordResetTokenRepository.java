package b3.CentroHospitalar.repositories;

import b3.CentroHospitalar.models.PasswordResetToken;
import b3.CentroHospitalar.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    PasswordResetToken findByUser(User user);
    PasswordResetToken findByToken(String token);

}

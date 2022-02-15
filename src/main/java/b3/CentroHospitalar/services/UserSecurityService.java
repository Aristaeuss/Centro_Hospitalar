package b3.CentroHospitalar.services;


import b3.CentroHospitalar.models.PasswordResetToken;
import b3.CentroHospitalar.repositories.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@Transactional
public class UserSecurityService implements ISecurityUserService {

    @Autowired
    PasswordResetTokenRepository prtr;

    @Override
    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = prtr.findByToken(token);
        return !isTokenFound(passToken) ? "invalidToken"
                : !isTokenExpired(passToken) ? "expired"
                : !isTokenUsed(passToken) ? "used"
                : null;
    }

    private boolean isTokenUsed(PasswordResetToken passToken) {
        return !passToken.isUsed();
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        return passToken.getExpiryDate().isAfter(LocalDate.now());
    }

    public void useToken(String token) {
        prtr.findByToken(token).setUsed(true);
    }
}

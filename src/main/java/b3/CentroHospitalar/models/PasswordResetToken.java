package b3.CentroHospitalar.models;

import b3.CentroHospitalar.models.users.User;
import b3.CentroHospitalar.repositories.PasswordResetTokenRepository;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class PasswordResetToken {

    public PasswordResetToken() {
    }

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;
    private boolean used;

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private LocalDate expiryDate;

    public static int getEXPIRATION() {
        return EXPIRATION;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public PasswordResetToken(String token, User user) {
        this.token=token;
        this.user=user;
        this.expiryDate=LocalDate.now().plusDays(1);
    }
}
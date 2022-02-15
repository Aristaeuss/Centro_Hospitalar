package b3.CentroHospitalar.services;

public interface ISecurityUserService {

    String validatePasswordResetToken(String token);

}

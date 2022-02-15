package b3.CentroHospitalar.services;

public interface EmailService {

    void sendSimpleMessage(String to, String subject, String text);
}

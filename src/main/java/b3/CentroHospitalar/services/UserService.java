package b3.CentroHospitalar.services;

import b3.CentroHospitalar.models.PasswordResetToken;
import b3.CentroHospitalar.models.users.*;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public interface UserService {

    User findUserByUsername(String username);
    User findUserByEmail(String email);
    List<Admin> findAllAdmins();
    Patient findPatientByUsername(String username);
    Employee findEmployeeByUsername(String username);
    Doctor findDoctorByUsername(String username);
    Doctor findDoctorByOrderNumber(int orderNumber);
    boolean okToRegisterEmployee(Employee employee);

    Patient findPatientByNif(int nif);
    void saveUser(User user);
    void saveUserWithoutPassword(User user);
    void updateUser(User user);
    User authenticate(String username, String password);

    Authentication getAuthentication();
    User getLoggedUser();

    void setSchedule(Doctor doctor, Schedule schedule);
    List<Doctor> getDoctors();

    List getUsers();


    void updateAddress(User user,String address, String door, String floor, String postalCode, String city);

    void updateTelephone(User user, String telephone);

    void updatePreferredName(User user, String name);

    void updateAboutMe(Doctor doctor, String aboutMe);

    void createPasswordResetTokenForUser(User user, String token);


    void saveDoctor(Doctor doctor, String specialityName);


    Doctor getDoctorById(int nif);

    ModelAndView verifyPatientRegister(Patient patient);

    void verifyPatientRegisterWithDefaultPassword(ModelAndView mav, Patient patient);
    void verifyPatientRegisterWithDefaultPasswordAdmin(ModelAndView mav, Patient patient);

    void verifyDoctorRegisterWithDefaultPassword(Doctor doctor, String specialityName, ModelAndView mav);

    void verifyDoctorRegister(Doctor doctor, String specialityName, ModelAndView mav);
    ModelAndView verifyEmployeeRegister(ModelAndView mav, Employee employee);

    ModelAndView verifyEmployeeRegisterWithDefaultPassword(ModelAndView mav, Employee employee);

    ModelAndView employeeVerifyEmployeeRegister(ModelAndView mav, Employee employee);
    ModelAndView employeeVerifyDoctorRegister(Doctor doctor);
    ModelAndView employeeVerifyPatientRegister(Patient patient);

    public boolean checkPatientEmail(String email);
    public boolean checkDoctorEmail(String email);
    public boolean checkEmployeeEmail(String email);
    public boolean checkUsername(String username);
    public boolean checkPatientNif(int nif);
    public boolean checkDoctorNif(int nif);
    public boolean checkDoctorOrderNumber(int orderNumber);
    public boolean checkEmployeeNif(int nif);
    public boolean checkPatientSocialSecurity(int socialSecurity);
    public boolean checkPassword(String password, String confirmPassword);

    public List<Doctor> findDoctorBySpeciality(Speciality speciality);

    void changePassword(User user, String password);
    List<Doctor> findDoctorsBySpecialityByDay(List<Doctor> doctorBySpeciality,String specialityName, LocalDate today);

    List<Doctor> getDoctorsBySpeciality(Speciality speciality);

    List<Patient> findPatientByName(String name);

    Doctor findDoctorByNif(int parseInt);

    void firstStartUp();

    Admin findAdminByUsername(String username);

    List<Doctor> findDoctorsScheduled();

    List<Doctor> findDoctorsBySpeciality(List<Doctor> doctorsScheduled, Speciality speciality);
}

package b3.CentroHospitalar.services;

import b3.CentroHospitalar.models.PasswordResetToken;
import b3.CentroHospitalar.models.Slot;
import b3.CentroHospitalar.models.users.*;
import b3.CentroHospitalar.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.print.Doc;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository ur;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    DoctorRepository dr;
    @Autowired
    EmployeeRepository er;
    @Autowired
    SpecialityService specialityService;
    @Autowired
    ScheduleService scheduleService;
    @Autowired
    SpecialityRepository specialityRepository;
    @Autowired
    PasswordResetTokenRepository prtr;
    @Autowired
    SlotService slotService;
    @Autowired
    AdminRepository adminRepository;


    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserService us;


    @Override
    public User findUserByUsername(String username) {
        return ur.findByUsername(username);
    }

    @Override
    public User findUserByEmail(String email) {
        return ur.findByEmail(email);
    }

    @Override
    public List<Admin> findAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public Patient findPatientByUsername(String username) {
        return patientRepository.findByUsername(username);
    }

    @Override
    public Employee findEmployeeByUsername(String username) {
        return er.findByUsername(username);
    }

    @Override
    public Doctor findDoctorByUsername(String username) {
        return dr.findByUsername(username);
    }

    @Override
    public Doctor findDoctorByOrderNumber(int orderNumber) {
        return dr.findByOrderNumber(orderNumber);
    }

    public boolean verifyDob(Date dob){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dob);
        LocalDate date= LocalDate.of(calendar.get(Calendar.YEAR),dob.getMonth()+1,dob.getDate());
        System.out.println(date);
        return !date.isAfter(LocalDate.now()) && !date.isBefore(LocalDate.of(1901, 01, 01));
    }

    @Override
    public boolean okToRegisterEmployee(Employee employee) {
        if(checkEmployeeEmail(employee.getEmail())) {
            return false;
        }
        if(checkEmployeeNif(employee.getNif())){
            return false;
        }
        if(checkUsername(employee.getUsername())){
            return false;
        }
        if(employee.getPassword()==null){
        saveUserWithoutPassword(employee);
        }else {saveUser(employee);}
        return true;
    }

    @Override
    public Patient findPatientByNif(int nif) {
        return patientRepository.findByNif(nif);
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        ur.save(user);
    }

    @Override
    public void saveUserWithoutPassword(User user) {
        ur.save(user);
    }

    @Override
    public void updateUser(User user) {
        ur.save(user);
    }

    @Override
    public User authenticate(String username, String password) {
        User user= ur.findByUsername(username);
        if(user==null) {
            return null;
        }
        if(passwordEncoder.matches(password,user.getPassword()))
            return user;
        return null;
    }

    @Override
    public Authentication getAuthentication() {
            return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public User getLoggedUser() {
       return ur.findByUsername(getAuthentication().getName());
    }

    @Override
    public void setSchedule(Doctor doctor, Schedule schedule) {

        doctor.setSchedule(schedule);
    }

    @Override
    public List<Doctor> getDoctors() {
        return dr.findAll();
    }



    @Override
    public List getUsers() {

        return ur.findAll();
    }

    @Override
    public void updateAddress(User user,String address, String door, String floor, String postalCode, String city) {
        user.setAddress(address);
        user.setDoorNumber(door);
        user.setFloor(floor);
        user.setPostalCode(postalCode);
        user.setCity(city);
        updateUser(user);
    }

    @Override
    public void updateTelephone(User user, String telephone) {
        int phone= Integer.parseInt(telephone);
        user.setTelephone(phone);
        updateUser(user);
    }

    @Override
    public void updatePreferredName(User user, String name) {
        user.setPreferredName(name);
        updateUser(user);
    }

    @Override
    public void updateAboutMe(Doctor doctor, String aboutMe) {
        doctor.setAboutMe(aboutMe);
        updateUser(doctor);
    }

    @Override
    public Doctor getDoctorById(int nif) {
        return dr.findByNif(nif);
    }

    @Override
    public void saveDoctor(Doctor doctor, String specialityName) {
        doctor.setSpeciality(specialityService.findByName(specialityName));
        saveUser(doctor);
        Schedule schedule= new Schedule(doctor);
        scheduleService.saveSchedule(schedule);
        doctor.setSchedule(schedule);
        updateUser(doctor);
    }



    @Override
    public boolean checkPatientEmail(String email) {
        return patientRepository.existsByEmail(email);
    }

    @Override
    public boolean checkDoctorEmail(String email) {
        return dr.existsByEmail(email);
    }

    @Override
    public boolean checkEmployeeEmail(String email) {
        return er.existsByEmail(email);
    }

    @Override
    public boolean checkUsername(String username) {
        if(patientRepository.existsByUsername(username)){
            return true;
        }
        if(er.existsByUsername(username)){
            return true;
        }
        return dr.existsByUsername(username);
    }

    @Override
    public boolean checkPatientNif(int nif) {
        return patientRepository.existsByNif(nif);
    }

    @Override
    public boolean checkDoctorNif(int nif) {
        return dr.existsByNif(nif);
    }

    @Override
    public boolean checkDoctorOrderNumber(int orderNumber) {
        return dr.existsByOrderNumber(orderNumber);
    }

    @Override
    public boolean checkEmployeeNif(int nif) {
        return er.existsByNif(nif);
    }

    @Override
    public boolean checkPatientSocialSecurity(int socialSecurity) {
        return patientRepository.existsBySocialSecurity(socialSecurity);
    }

    @Override
    public boolean checkPassword(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    @Override
    public List<Doctor> findDoctorBySpeciality(Speciality speciality) {
       return dr.findAllBySpeciality(speciality);
    }

    @Override
    public void changePassword(User user, String password) {
        user.setPassword(password);
    }
    @Override
    public List<Doctor> findDoctorsBySpecialityByDay(List<Doctor> doctorBySpeciality, String specialityName, LocalDate today) {
        List<Slot> slots = slotService.slotsAvailableBySpecialityAndDate(specialityName,today);
        List<Doctor> doctors=new ArrayList<>();
        System.out.println("doctors list initialized "+doctors);
        for(Doctor doctor:doctorBySpeciality){
            for(Slot slot:slots){
                if(doctor.getOrderNumber()==slot.getDoctor().getOrderNumber()){
                    if(!doctors.contains(doctor)){
                        doctors.add(doctor);
                    }
                }
            }
        }
        return doctors;
    }

    @Override
    public List<Patient> findPatientByName(String name) {

        return patientRepository.findByNameContaining(name);
    }

    @Override
    public Doctor findDoctorByNif(int parseInt) {
        return dr.findByNif(parseInt);
    }

    @Override
    public List<Doctor> getDoctorsBySpeciality(Speciality speciality) {
        return dr.findAllBySpeciality(speciality);
    }


    @Override
    public ModelAndView verifyPatientRegister(Patient patient) {
        ModelAndView mav= new ModelAndView();
        boolean failed = false;
        if(checkPatientEmail(patient.getEmail())){
            mav.addObject("errorMessageEmail", "O email indicado já está registado: <a href=\"recuperarAcesso\">Recuperar password</a>");
            failed = true;
        }
        if(checkUsername(patient.getUsername())){
            mav.addObject("errorMessageUsername", "O username indicado já existe, escolha um username diferente");
            failed = true;
        }
        if (checkPatientNif(patient.getNif())){
            mav.addObject("errorMessageNif", "O NIF indicado já está registado: <a href=\"recuperarAcesso\">Recuperar password</a>");
            failed = true;
        }
        if(checkPatientSocialSecurity(patient.getSocialSecurity())){
            mav.addObject("errorMessageSocialSecurity", "O NISS indicado já está registado: <a href=\"recuperarAcesso\">Recuperar password</a>");
            failed = true;
        }
        if(checkPatientNumber(patient.getPatientNumber())){
            mav.addObject("errorMessagePatientNumber", "O número de utente indicado já está registado: <a href=\"recuperarAcesso\">Recuperar password</a>");
            failed = true;
        }
        if(!checkPassword(patient.getPassword(),patient.getConfirmPassword())){
            mav.addObject("errorMessagePassword", "Por favor verifique a password.");
            failed = true;
        }
        if(!verifyDob(patient.getDob())){
            mav.addObject("errorMessageDob", "Por favor selecione uma data de nascimento válida.");
            failed = true;
        }
        if (!failed) {
            saveUser(patient);
            return new ModelAndView("/login");
        } else {
            mav.setViewName("registo");
        }
        return mav;
    }

    @Override
    public void verifyPatientRegisterWithDefaultPassword(ModelAndView mav, Patient patient) {
        patient.setPassword(patient.getNif()+"");
        boolean failed = false;
        if(checkPatientEmail(patient.getEmail())){
            mav.addObject("errorMessageEmail", "O email indicado já está registado: <a href=\"recuperarAcesso\">Recuperar password</a>");
            failed = true;
        }
        if(checkUsername(patient.getUsername())){
            mav.addObject("errorMessageUsername", "O username indicado já existe, escolha um username diferente");
            failed = true;
        }
        if (checkPatientNif(patient.getNif())){
            mav.addObject("errorMessageNif", "O NIF indicado já está registado: <a href=\"recuperarAcesso\">Recuperar password</a>");
            failed = true;
        }
        if(checkPatientSocialSecurity(patient.getSocialSecurity())){
            mav.addObject("errorMessageSocialSecurity", "O NISS indicado já está registado: <a href=\"recuperarAcesso\">Recuperar password</a>");
            failed = true;
        }
        if(checkPatientNumber(patient.getPatientNumber())){
            mav.addObject("errorMessagePatientNumber", "O número de utente indicado já está registado: <a href=\"recuperarAcesso\">Recuperar password</a>");
            failed = true;
        }
        if(!verifyDob(patient.getDob())){
            mav.addObject("errorMessageDob", "Por favor selecione uma data de nascimento válida.");
            failed = true;
        }
        if (!failed) {
            saveUser(patient);
        } else {
            mav.setViewName("RegistosFuncionarioU");
        }
    }

    @Override
    public void verifyPatientRegisterWithDefaultPasswordAdmin(ModelAndView mav, Patient patient) {
        patient.setPassword(patient.getNif()+"");
        boolean failed = false;
        if(checkPatientEmail(patient.getEmail())){
            mav.addObject("errorMessageEmail", "O email indicado já está registado: <a href=\"recuperarAcesso\">Recuperar password</a>");
            failed = true;
        }
        if(checkUsername(patient.getUsername())){
            mav.addObject("errorMessageUsername", "O username indicado já existe, escolha um username diferente");
            failed = true;
        }
        if (checkPatientNif(patient.getNif())){
            mav.addObject("errorMessageNif", "O NIF indicado já está registado: <a href=\"recuperarAcesso\">Recuperar password</a>");
            failed = true;
        }
        if(checkPatientSocialSecurity(patient.getSocialSecurity())){
            mav.addObject("errorMessageSocialSecurity", "O NISS indicado já está registado: <a href=\"recuperarAcesso\">Recuperar password</a>");
            failed = true;
        }
        if(checkPatientNumber(patient.getPatientNumber())){
            mav.addObject("errorMessagePatientNumber", "O número de utente indicado já está registado: <a href=\"recuperarAcesso\">Recuperar password</a>");
            failed = true;
        }
        if(!verifyDob(patient.getDob())){
            mav.addObject("errorMessageDob", "Por favor selecione uma data de nascimento válida.");
            failed = true;
        }
        if (!failed) {
            saveUser(patient);
        } else {
            mav.setViewName("RegistosFuncionarioA");
        }
    }

    private boolean checkPatientNumber(int patientNumber) {
        return patientRepository.existsByPatientNumber(patientNumber);
    }

    @Override
    public void verifyDoctorRegisterWithDefaultPassword(Doctor doctor, String specialityName, ModelAndView mav) {
        boolean failed = false;
        doctor.setPassword(doctor.getNif()+"");
        if(checkDoctorEmail(doctor.getEmail())){
            mav.addObject("errorMessageEmail", "O email indicado já está registado: <a href=\"recuperarAcesso\">Recuperar password</a>");
            failed = true;
        }
        if(checkDoctorOrderNumber(doctor.getOrderNumber())){
            mav.addObject("errorMessageOrderNumber", "O número de ordem indicado já existe:  <a href=\"recuperarAcesso\">Recuperar password</a>");
            failed = true;
        }
        if(checkUsername(doctor.getUsername())){
            mav.addObject("errorMessageUsername", "O username indicado já existe, escolha um username diferente");
            failed = true;
        }
        if (checkDoctorNif(doctor.getNif())){
            mav.addObject("errorMessageNif", "O NIF indicado já está registado: <a href=\"recuperarAcesso\">Recuperar password</a>");
            failed = true;
        }
        if(checkDoctorOrderNumber(doctor.getOrderNumber())) {
            mav.addObject("errorMessageOrderNumber", "O número de ordem já está registado: <a href=\"recuperarAcesso\">Recuperar password</a>");
            failed = true;
        }
        if(!verifyDob(doctor.getDob())){
            mav.addObject("errorMessageDob", "Por favor selecione uma data de nascimento válida.");
            failed = true;
        }
        if (!failed) {
            saveDoctor(doctor, specialityName);
        } else {
            mav.addObject("specialities",specialityRepository.findAll());
            mav.setViewName("/RegistosFuncionarioM");
        }
    }


    @Override
    public void verifyDoctorRegister(Doctor doctor,String specialityName, ModelAndView mav) {
        boolean failed = false;
        if(checkDoctorEmail(doctor.getEmail())){
            mav.addObject("errorMessageEmail", "O email indicado já está registado: <a href=\"recuperarAcesso\">Recuperar password</a>");
            failed = true;
        }
        if(checkDoctorOrderNumber(doctor.getOrderNumber())){
            mav.addObject("errorMessageOrderNumber", "O número de ordem indicado já existe:  <a href=\"recuperarAcesso\">Recuperar password</a>");
            failed = true;
        }
        if(checkUsername(doctor.getUsername())){
            mav.addObject("errorMessageUsername", "O username indicado já existe, escolha um username diferente");
            failed = true;
        }
        if (checkDoctorNif(doctor.getNif())){
            mav.addObject("errorMessageNif", "O NIF indicado já está registado: <a href=\"recuperarAcesso\">Recuperar password</a>");
            failed = true;
        }
        if(checkDoctorOrderNumber(doctor.getOrderNumber())){
            mav.addObject("errorMessageOrderNumber","O número de ordem já está registado: <a href=\"recuperarAcesso\">Recuperar password</a>");
            failed = true;
        }
        if(!checkPassword(doctor.getPassword(),doctor.getConfirmPassword())){
            mav.addObject("errorMessagePassword", "Por favor verifique a password.");
            failed = true;
        }
        if(!verifyDob(doctor.getDob())){
            mav.addObject("errorMessageDob", "Por favor selecione uma data de nascimento válida.");
            failed = true;
        }
        if (!failed) {
            saveDoctor(doctor, specialityName);
        } else {
            mav.setViewName("registo");
        }
    }

    @Override
    public ModelAndView verifyEmployeeRegister(ModelAndView mav, Employee employee) {
        boolean failed = false;
        if(checkEmployeeEmail(employee.getEmail())){
            mav.addObject("errorMessageEmail", "O email indicado já está registado: <a href=\"recuperarAcesso\">Recuperar password</a>");
            failed = true;
        }
        if(checkUsername(employee.getUsername())){
            mav.addObject("errorMessageUsername", "O username indicado já existe, escolha um username diferente");
            failed = true;
        }
        if (checkEmployeeNif(employee.getNif())){
            mav.addObject("errorMessageNif", "O NIF indicado já está registado: <a href=\"recuperarAcesso\">Recuperar password</a>");
            failed = true;
        }
        if(!checkPassword(employee.getPassword(),employee.getConfirmPassword())){
            mav.addObject("errorMessagePassword", "Por favor verifique a password.");
            failed = true;
        }
        if(!verifyDob(employee.getDob())){
            mav.addObject("errorMessageDob", "Por favor selecione uma data de nascimento válida.");
            failed = true;
        }
        if (!failed) {
            saveUser(employee);
            return new ModelAndView("/login");
        } else {
            mav.setViewName("registo");
        }
        return mav;
    }

    @Override
    public ModelAndView verifyEmployeeRegisterWithDefaultPassword(ModelAndView mav, Employee employee) {
        boolean failed = false;
        employee.setPassword(employee.getNif()+"");
        if(checkEmployeeEmail(employee.getEmail())){
            mav.addObject("errorMessageEmail", "O email indicado já está registado: <a href=\"recuperarAcesso\">Recuperar password</a>");
            failed = true;
        }
        if(checkUsername(employee.getUsername())){
            mav.addObject("errorMessageUsername", "O username indicado já existe, escolha um username diferente");
            failed = true;
        }
        if (checkEmployeeNif(employee.getNif())) {
            mav.addObject("errorMessageNif", "O NIF indicado já está registado: <a href=\"recuperarAcesso\">Recuperar password</a>");
            failed = true;
        }
        if(!verifyDob(employee.getDob())){
            mav.addObject("errorMessageDob", "Por favor selecione uma data de nascimento válida.");
            failed = true;
        }
        if (!failed) {
            saveUser(employee);
            return new ModelAndView("RegistosFuncionarioF");
        } else {
            mav.setViewName("RegistosAdmin");
        }
        return mav;
    }


    @Override
    public ModelAndView employeeVerifyEmployeeRegister(ModelAndView mav,Employee employee) {
        boolean failed = false;
        if(checkEmployeeEmail(employee.getEmail())){
            mav.addObject("errorMessageEmail", "O email indicado já está registado: <a href=\"forgotPassword\">Recuperar password</a>");
            failed = true;
        }
        if(checkUsername(employee.getUsername())){
            mav.addObject("errorMessageUsername", "O username indicado já existe, escolha um username diferente");
            failed = true;
        }
        if (checkEmployeeNif(employee.getNif())){
            mav.addObject("errorMessageNif", "O NIF indicado já está registado: <a href=\"forgotPassword\">Recuperar password</a>");
            failed = true;
        }
        if(!verifyDob(employee.getDob())){
            mav.addObject("errorMessageDob", "Por favor selecione uma data de nascimento válida.");
            failed = true;
        }
        if (!failed) {
            saveUser(employee);
            return new ModelAndView("/login");
        } else {
            mav.setViewName("registo");
        }
        return mav;
    }

    @Override
    public ModelAndView employeeVerifyDoctorRegister(Doctor doctor) {
        ModelAndView mav= new ModelAndView();
        boolean failed = false;
        if(checkDoctorEmail(doctor.getEmail())){
            mav.addObject("errorMessageEmail", "O email indicado já está registado: <a href=\"forgotPassword\">Recuperar password</a>");
            failed = true;
        }
        if(checkUsername(doctor.getUsername())){
            mav.addObject("errorMessageUsername", "O username indicado já existe, escolha um username diferente");
            failed = true;
        }
        if (checkDoctorNif(doctor.getNif())){
            mav.addObject("errorMessageNif", "O NIF indicado já está registado: <a href=\"forgotPassword\">Recuperar password</a>");
            failed = true;
        }
        if(!verifyDob(doctor.getDob())){
            mav.addObject("errorMessageDob", "Por favor selecione uma data de nascimento válida.");
            failed = true;
        }
        if (!failed) {
            saveUser(doctor);
            return new ModelAndView("/login");
        } else {
            mav.setViewName("registo");
        }
        return mav;
    }

    @Override
    public ModelAndView employeeVerifyPatientRegister(Patient patient) {
        ModelAndView mav= new ModelAndView();
        boolean failed = false;
        if(checkPatientEmail(patient.getEmail())){
            mav.addObject("errorMessageEmail", "O email indicado já está registado: <a href=\"forgotPassword\">Recuperar password</a>");
            failed = true;
        }
        if(checkUsername(patient.getUsername())){
            mav.addObject("errorMessageUsername", "O username indicado já existe, escolha um username diferente");
            failed = true;
        }
        if (checkPatientNif(patient.getNif())){
            mav.addObject("errorMessageNif", "O NIF indicado já está registado: <a href=\"forgotPassword\">Recuperar password</a>");
            failed = true;
        }
        if(!verifyDob(patient.getDob())){
            mav.addObject("errorMessageDob", "Por favor selecione uma data de nascimento válida.");
            failed = true;
        }
        if (!failed) {
            saveUser(patient);
            return new ModelAndView("/login");
        } else {
            mav.setViewName("registo");
        }
        return mav;
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        prtr.save(myToken);
    }

    @Override
    public Admin findAdminByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    @Override
    public List<Doctor> findDoctorsScheduled() {
        List<Doctor> doctors=dr.findAll();
        List<Doctor> doctorsPresent=new ArrayList<>();

        for(Doctor doctor:doctors){
            List<Slot> slotsToday=slotService.getSlotsForDoctorAndDate(doctor,LocalDate.now());
            for(Slot slot:slotsToday){
                if(slot.getDateTime().getHour()== LocalDateTime.now().getHour()&& !doctorsPresent.contains(doctor)){
                    doctorsPresent.add(doctor);
                }
            }
        }
        return doctorsPresent;
    }

    @Override
    public List<Doctor> findDoctorsBySpeciality(List<Doctor> doctorsScheduled, Speciality speciality) {
        List<Doctor> doctorsBySpeciality=new ArrayList<>();
        for(Doctor doctor:doctorsScheduled){
            if(doctor.getSpeciality().equals(speciality)){
                doctorsBySpeciality.add(doctor);
            }
        }
        return doctorsBySpeciality;
    }


    //corre apenas na primeira vez que se corre o programa, cria 1 Admin, 3 Doctors, 2 Patient, 1 Employee
    //6 especialidades e slots para os medicos para este mês e o próximo
    @Override
    public void firstStartUp() {
        Admin admin=new Admin();
        admin.setUsername("admin");
        admin.setPreferredName("Administrador");
        admin.setPassword("admin");
        admin.setPassword("admin");
        admin.setEmail("upskill.clinic@gmail.com");
        saveUser(admin);
        Speciality speciality1=new Speciality("Cardiologia");
        Speciality speciality2=new Speciality("Cirurgia Geral");
        Speciality speciality3=new Speciality("Medicina Geral e Familiar");
        Speciality speciality4=new Speciality("Neurologia");
        Speciality speciality5=new Speciality("Oftalmologia");
        Speciality speciality6=new Speciality("Pediatria");
        specialityRepository.save(speciality1);
        specialityRepository.save(speciality2);
        specialityRepository.save(speciality3);
        specialityRepository.save(speciality4);
        specialityRepository.save(speciality5);
        specialityRepository.save(speciality6);
        Doctor doctor1=new Doctor("doctor1","test","António Cardoso Silva","Antonio Silva",123,new Date(1980-1900, Calendar.APRIL,20),123,"doctor1@gmail.com",123,123,"Formado na FMUL, especialista em Cardiologia, com anos de experiência");
        Doctor doctor2=new Doctor("doctor2","test","Maria Amélia dos Anjos","Amélia Anjos",321,new Date(1970-1900, Calendar.DECEMBER,15),321,"doctor2@gmail.com",321,321,"Formada na FMUP, especialista em Neurocirurgia, investigadora com mais de 30 artigos publicados");
        Doctor doctor3=new Doctor("doctor3","test","Luís Gomes da Costa","Luís Costa",789,new Date(1990-1900, Calendar.FEBRUARY,5),789,"doctor3@gmail.com",789,789,"Pediatra com conduta humanizada e holística.");
        saveDoctor(doctor1,"Cardiologia");
        saveDoctor(doctor2,"Neurologia");
        saveDoctor(doctor3,"Pediatria");
        Patient patient1= new Patient("patient1","test","Rui Castro Marques","Rui Marques",987,987,987,new Date(1999-1900, Calendar.JUNE,4),987,"patient1@gmail.com");
        Patient patient2= new Patient("patient2","test","Luisa Lima Madruga","Luisa Madruga",654,654,654,new Date(2002-1900, Calendar.JULY,7),654,"patient2@gmail.com");
        saveUser(patient1);
        saveUser(patient2);
        Employee employee=new Employee("employee","test","Miguel Pêra","Miguel Pêra",456,new Date(1970-1900, Calendar.SEPTEMBER,10),456,"employee@gmail.com",456);
        saveUser(employee);
        slotService.addSlotsForAllDoctorsForMonth(YearMonth.now());
        slotService.addSlotsForAllDoctorsForMonth(slotService.nextMonth());
    }

}

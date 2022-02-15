package b3.CentroHospitalar.models.users;


import b3.CentroHospitalar.models.ScheduledAppointment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.*;

@Entity
public class Patient extends User{

    private int socialSecurity;
    private int patientNumber;

    @OneToMany
    Set<ScheduledAppointment> appointments;

    public Patient(){}
    public Patient(String username, String password, String name, String preferedName, int nif, int socialSecurity, int patientNumber, Date dob, int telephone, String email) {
        super(username,password,name,preferedName,nif,dob,telephone,email);
    }


    @Override
    public List<GrantedAuthority> getAuthorities(){
        List<GrantedAuthority> roles=new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(Roles.ROLE_PATIENT.name()));
        return roles;
    }

    public Set<ScheduledAppointment> getAppointments() {
        return appointments;
    }

    public void addAppointment(ScheduledAppointment appointment) {
        this.appointments.add(appointment);
    }

    public int getSocialSecurity() {
        return socialSecurity;
    }

    public void setSocialSecurity(int socialSecurity) {
        this.socialSecurity = socialSecurity;
    }

    public int getPatientNumber() {
        return patientNumber;
    }

    public void setPatientNumber(int patientNumber) {
        this.patientNumber = patientNumber;
    }

    public void setAppointments(Set<ScheduledAppointment> appointments) {
        this.appointments = appointments;
    }


    @Override
    public String toString(){
        return super.getName()+getEmail()+getUsername()+getDob()+getNif();
    }
}

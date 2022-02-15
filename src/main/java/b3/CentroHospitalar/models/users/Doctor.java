package b3.CentroHospitalar.models.users;

import b3.CentroHospitalar.models.Slot;
import b3.CentroHospitalar.models.Ticket;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="doctor")
public class Doctor extends User{

    private int orderNumber;
    private int iban;
    private String aboutMe;
    public Doctor(){}

    @OneToOne
    private Ticket appointmentTicketInProgress;

    @ManyToOne
    @JoinColumn(name = "speciality_id")
    private Speciality speciality;


    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "doctor_slot", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "slot_id"))
    private Set<Slot> slots = new HashSet<>();

    @OneToOne
    private Schedule schedule;


    @Override
    public List<GrantedAuthority> getAuthorities(){
        List<GrantedAuthority> roles=new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(Roles.ROLE_DOCTOR.name()));
        return roles;
    }

    public Doctor(String username, String password, String name, String preferredName, int nif, Date dob, int telephone, String email, int orderNumber, int iban, String aboutMe) {
        super(username, password, name, preferredName, nif, dob, telephone, email);
        this.orderNumber = orderNumber;
        this.iban = iban;
        this.aboutMe = aboutMe;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getIban() {
        return iban;
    }

    public void setIban(int iban) {
        this.iban = iban;
    }

    public int getOrderNumber(){
        return orderNumber;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }


    public String getAboutMe() {
        return aboutMe;
    }
    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public Set<Slot> getSlots() {
        return slots;
    }

    public void addSlot(Slot slot){
        slots.add(slot);
    }

    public void setSlots(Set<Slot> slots) {
        this.slots = slots;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Ticket getAppointmentTicketInProgress() {
        return appointmentTicketInProgress;
    }

    public void setAppointmentTicketInProgress(Ticket appointmentTicketInProgress) {
        this.appointmentTicketInProgress = appointmentTicketInProgress;
    }

    @Override
    public UserImage getUserImage() {
        if(image==null){
            return new UserImage(this,"../Images/perfilMedico.svg");
        }
        return image;
    }
}

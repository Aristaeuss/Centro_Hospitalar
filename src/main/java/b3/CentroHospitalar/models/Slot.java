package b3.CentroHospitalar.models;

import b3.CentroHospitalar.models.users.Doctor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;

@Entity
public class Slot {

    public static final int NUM_MINUTES_PER_SLOT = 30;
    public static final int MAX_NUM_MINUTES_LATE = 10;

    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime dateTime;
    private String specialityName;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Doctor doctor;

    @OneToOne
    private ScheduledAppointment scheduledAppointment;

    public ScheduledAppointment getScheduledAppointment() {
        return scheduledAppointment;
    }

    public void setScheduledAppointment(ScheduledAppointment scheduledAppointment) {
        this.scheduledAppointment = scheduledAppointment;
    }

    public String getSpecialityName() {

        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    public Slot(){}
    public Slot(LocalDateTime dateTime, Doctor doctor, String specialityName){
        this.dateTime = dateTime;
        this.doctor=doctor;
        this.specialityName=specialityName;
        this.scheduledAppointment = null;
        this.date= this.dateTime.toLocalDate();
    }

    public boolean isAvailable() {
        return this.scheduledAppointment == null;
    }

    public void makeAvailable() {
        this.scheduledAppointment = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDate getDate(){
        return date;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slot slot = (Slot) o;
        return Objects.equals(id, slot.id) && Objects.equals(dateTime, slot.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateTime);
    }
}

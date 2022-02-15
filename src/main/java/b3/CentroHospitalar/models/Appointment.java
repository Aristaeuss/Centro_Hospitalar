package b3.CentroHospitalar.models;

import b3.CentroHospitalar.models.users.Doctor;
import b3.CentroHospitalar.models.users.Patient;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Appointment {

    @Id
    @GeneratedValue
    private long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean absentPatient;
    private String title;
    private String note;
    private String prescription;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Doctor doctor;

    @OneToOne
    private ScheduledAppointment scheduledAppointment;

    @OneToOne
    private Invoice invoice;

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Appointment(){}

    public Appointment(ScheduledAppointment scheduledAppointment){
        this.scheduledAppointment=scheduledAppointment;
        this.patient=scheduledAppointment.getPatient();
        this.doctor=scheduledAppointment.getSlot().getDoctor();
        startTime=startTime.now();
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public boolean isAbsentPatient() {
        return absentPatient;
    }

    public void setAbsentPatient(boolean absentPatient) {
        this.absentPatient = absentPatient;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public ScheduledAppointment getScheduledAppointment() {
        return scheduledAppointment;
    }

    public void setScheduledAppointment(ScheduledAppointment scheduledAppointment) {
        this.scheduledAppointment = scheduledAppointment;
    }

    public void closeAppointment(String title, String note, String prescription){
        setTitle(title);
        setNote(note);
        setPrescription(prescription);
        setEndTime(LocalDateTime.now());
    }

    public void closeAppointmentNoShow(){
        setEndTime(LocalDateTime.now());
        setAbsentPatient(true);
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }


}

package b3.CentroHospitalar.models;

import b3.CentroHospitalar.models.users.Patient;
import org.springframework.context.ApplicationContext;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class ScheduledAppointment {
    @Id
    @GeneratedValue
    private Long id;
    private boolean canceledByUtente;
    private boolean canceledByHospital;
    private boolean isNowAnAppointment;
    private LocalDate creationDate;

    @OneToOne
    private Slot slot;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Patient patient;

    @OneToOne
    private Ticket ticket;

    @OneToOne
    private Appointment appointment;


    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public ScheduledAppointment(){}
    public ScheduledAppointment(Slot slot, Patient patient){
        if (!slot.isAvailable())
            throw new IllegalStateException("Slot is already occupied.");
        this.slot=slot;
        this.patient=patient;
        slot.setScheduledAppointment(this);
        patient.addAppointment(this);
        this.creationDate=LocalDate.now();
    }

    public String getState() {
        if (!isNowAnAppointment) {
            if (canceledByHospital) {
                return "Cancelada pelo Hospital";
            }
            if (canceledByUtente) {
                return "Cancelada pelo Utente";
            }
            return "Pendente";
        }
        return "Concluida";
    }

    public boolean isCanceled() {
        return canceledByHospital || canceledByUtente;
    }

    public boolean isNowAnAppointment() {
        return isNowAnAppointment;
    }

    public void setNowAnAppointment(boolean nowAnAppointment) {
        isNowAnAppointment = nowAnAppointment;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isCanceledByUtente() {
        return canceledByUtente;
    }

    public void setCanceledByUtente(boolean canceledByUtente) {
        this.canceledByUtente = canceledByUtente;
    }

    public boolean isCanceledByHospital() {
        return canceledByHospital;
    }

    public void setCanceledByHospital(boolean canceledByHospital) {
        this.canceledByHospital = canceledByHospital;
    }

}

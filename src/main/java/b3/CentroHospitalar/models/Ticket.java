package b3.CentroHospitalar.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class Ticket {

    @Id
    @GeneratedValue
    private long id;
    private LocalDateTime checkInDateTime;
    private boolean called;
    private LocalDateTime calledTime;
    private boolean rejectedForLateness;
    private LocalDateTime rejectedDateTime;
    private boolean appointmentFinished;

    public LocalDateTime getRejectedDateTime() {
        return rejectedDateTime;
    }

    public void setRejectedDateTime(LocalDateTime rejectedDateTime) {
        this.rejectedDateTime = rejectedDateTime;
    }

    @OneToOne
    private ScheduledAppointment scheduledAppointment;

    public boolean isRejectedForLateness() {
        return rejectedForLateness;
    }

    public void setRejectedForLateness(boolean rejectedForLateness) {
        this.rejectedForLateness = rejectedForLateness;
    }

    public Ticket(){};

    public Ticket(ScheduledAppointment scheduledAppointment){
        this.scheduledAppointment=scheduledAppointment;
        checkInDateTime=LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getCheckInDateTime() {
        return checkInDateTime;
    }

    public void setCheckInDateTime(LocalDateTime checkInDateTime) {
        this.checkInDateTime = checkInDateTime;
    }

    public boolean isCalled() {
        return called;
    }

    public void setCalled(boolean called) {
        this.called = called;
    }

    public ScheduledAppointment getScheduledAppointment() {
        return scheduledAppointment;
    }

    public void setScheduledAppointment(ScheduledAppointment scheduledAppointment) {
        this.scheduledAppointment = scheduledAppointment;
    }

    public LocalDateTime getCalledTime() {
        return calledTime;
    }

    public void setCalledTime(LocalDateTime calledTime) {
        this.calledTime = calledTime;
    }

    public boolean isAppointmentFinished() {
        return appointmentFinished;
    }

    public void setAppointmentFinished(boolean appointmentFinished) {
        this.appointmentFinished = appointmentFinished;
    }
}

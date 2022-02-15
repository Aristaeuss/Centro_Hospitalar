package b3.CentroHospitalar.models.users;

import javax.persistence.*;
import javax.print.Doc;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.ScheduledExecutorService;

@Entity
public class Schedule {

    public static final int NUM_WORK_HOURS = 9;
    public static final int NUM_LUNCH_TIME = 1;

    @Id
    @GeneratedValue
    private long id;
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private boolean sunday;
    private LocalTime startTime;
    private LocalTime startLunch;
    private LocalTime endLunch;
    private LocalTime endTime;
    private boolean endsNextDay;


    @OneToOne
    private Doctor doctor;

    public Schedule(){}

    public Schedule(boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday, LocalTime startTime, LocalTime startLunch) {
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        this.startTime = startTime;
        this.startLunch = startLunch;
    }

    public Schedule(Doctor doctor){
        monday=true;
        tuesday=true;
        wednesday=true;
        thursday=true;
        friday=true;
        this.doctor=doctor;
        this.startTime=LocalTime.of(8,00,00);
        this.endTime=LocalTime.of(17,00,00);
        this.startLunch=LocalTime.of(13,00,00);
        this.endLunch=LocalTime.of(14,00,00);
    }

    public boolean isMonday() {
        return monday;
    }

    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public boolean isTuesday() {
        return tuesday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public boolean isWednesday() {
        return wednesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }

    public boolean isThursday() {
        return thursday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public boolean isFriday() {
        return friday;
    }

    public void setFriday(boolean friday) {
        this.friday = friday;
    }

    public boolean isSaturday() {
        return saturday;
    }

    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }

    public boolean isSunday() {
        return sunday;
    }

    public void setSunday(boolean sunday) {
        this.sunday = sunday;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(int sT) {
        LocalTime time= LocalTime.of(sT,0,0,0);
        this.startTime = time;
        this.endTime=startTime.plusHours(NUM_WORK_HOURS);
        endsNextDay=endTime.isBefore(startTime);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public boolean isEndsNextDay() {
        return endsNextDay;
    }

    public void setEndsNextDay(boolean endsNextDay) {
        this.endsNextDay = endsNextDay;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalTime getStartLunch() {
        return startLunch;
    }

    public void setStartLunch(int startL) {
        LocalTime time= LocalTime.of(startL,0,0,0);
        this.startLunch = time;
        this.endLunch=startLunch.plusHours(NUM_LUNCH_TIME);
    }

    public LocalTime getEndLunch() {
        return endLunch;
    }

    public void setEndLunch(LocalTime endLunch) {
        this.endLunch = endLunch;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}

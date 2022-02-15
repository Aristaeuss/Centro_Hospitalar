package b3.CentroHospitalar.services;

import b3.CentroHospitalar.models.ScheduledAppointment;
import b3.CentroHospitalar.models.Slot;
import b3.CentroHospitalar.models.users.Doctor;
import b3.CentroHospitalar.models.users.User;
import b3.CentroHospitalar.services.Exceptions.IncorrectUserTypeException;
import b3.CentroHospitalar.services.Exceptions.NoSuchSlotException;
import b3.CentroHospitalar.services.Exceptions.SlotAlreadyBookedException;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

public interface SlotService {

    public YearMonth nextMonth();
    void addSlotsForAllDoctorsForMonth(YearMonth month);
    void addSlotsForDoctorForMonth(Doctor doctor, YearMonth month);
    ScheduledAppointment bookSlot(Long slotId, User user) throws IncorrectUserTypeException, NoSuchSlotException, SlotAlreadyBookedException;

    List<Integer> daysAvailableByMonthAndSpeciality(String speciality, int month);
    List<Integer> daysAvailableByMonthAndSpecialityThatHaveNotHappenedYet(String speciality, int month);
    ModelAndView daysBeforeFilter();

    List<Slot> slotsAvailableBySpecialityAndDate(String specialityName, LocalDate date);

    List<Slot> slotsAvailableBySpecialityAndDateThatHaveNotHappenedYet(String specialityName, LocalDate date);

    ModelAndView getAvailabilitiesBySpeciality(String specialityName);

    ModelAndView getAvailabilitiesByDoctor(int doctorOrderNumber);

    ModelAndView checkDoctorsAvailableByDateAndSpeciality(String dayMonthString);
    ModelAndView checkSlotsAvailableByDoctorAndDate(String dateDoctor);

    List<Slot> getSlotsForDoctorAndDate(Doctor doctor, LocalDate date);

    List<Slot> getSlotsForDoctorAndDateThatHaveNotHappenedYet(Doctor doctor, LocalDate date);

    List<Slot> slotsAvailableForFirstDoctorAndDate(List doctorsAvailableTodayBySpeciality, LocalDate date);

    List<Slot> slotsAvailableForFirstDoctorAndDateThatHaveNotHappenedYet(List doctorsAvailableTodayBySpeciality, LocalDate date);

    Slot findById(Long slotID);
}

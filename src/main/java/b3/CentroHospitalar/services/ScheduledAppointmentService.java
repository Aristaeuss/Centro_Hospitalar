package b3.CentroHospitalar.services;

import b3.CentroHospitalar.models.ScheduledAppointment;
import b3.CentroHospitalar.models.users.Doctor;
import b3.CentroHospitalar.models.users.User;
import b3.CentroHospitalar.services.Exceptions.IncorrectUserTypeException;
import b3.CentroHospitalar.services.Exceptions.ScheduledAppointmentAlreadyCanceledException;

import java.time.LocalDate;
import java.util.List;

public interface ScheduledAppointmentService {

    ScheduledAppointment todaysNextNonCanceledNotAppointmentYetScheduledAppointmentOf(User user) throws IncorrectUserTypeException;

    List<ScheduledAppointment> allNonCanceledNotYetHappenedAppointmentsOf(User user) throws IncorrectUserTypeException;

    List<ScheduledAppointment> allNonCanceledAppointmentsOf(User user) throws IncorrectUserTypeException;

    ScheduledAppointment findById(Long scheduledAppointmentId);

    void cancel(ScheduledAppointment scheduledAppointment, boolean byHospitalNotUser) throws ScheduledAppointmentAlreadyCanceledException;

    List<ScheduledAppointment> allScheduledAppointmentsForDoctorByDateNotYetAppointment(Doctor doctor, LocalDate date);

    List<ScheduledAppointment> allScheduledAppointmentsForDoctorByDate(Doctor doctor, LocalDate date);

    void makeItAnAppointment(ScheduledAppointment scheduledAppointment);

    ScheduledAppointment nextAppointmentForDoctor(Doctor loggedUser);

    List<ScheduledAppointment> allLateNotCheckedInScheduledAppointments();

    List<ScheduledAppointment> allCanceledAppointmentsOf(User loggedUser);

    List<ScheduledAppointment> allNotYetHappenedScheduledAppointmentsOf(User user);

    ScheduledAppointment nextNonCanceledNotAppointmentYetScheduledAppointmentOf(User user) throws IncorrectUserTypeException;
}

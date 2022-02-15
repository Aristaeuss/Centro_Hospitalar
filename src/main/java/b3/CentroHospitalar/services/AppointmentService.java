package b3.CentroHospitalar.services;

import b3.CentroHospitalar.models.Appointment;
import b3.CentroHospitalar.models.ScheduledAppointment;
import b3.CentroHospitalar.models.users.Patient;

import java.util.List;
import java.util.Optional;

public interface AppointmentService {

    void officializeAppointment(Appointment appointment, ScheduledAppointment scheduledAppointment);

    List<Appointment> findAllByPatient(Patient loggedUser);

    Optional<Appointment> findById(long parseLong);
}

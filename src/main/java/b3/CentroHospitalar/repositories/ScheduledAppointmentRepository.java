package b3.CentroHospitalar.repositories;

import b3.CentroHospitalar.models.ScheduledAppointment;
import b3.CentroHospitalar.models.Ticket;
import b3.CentroHospitalar.models.users.Patient;
import b3.CentroHospitalar.models.users.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduledAppointmentRepository extends JpaRepository<ScheduledAppointment,Long> {

    List<ScheduledAppointment> findAllByPatient(Patient patient);

    List<ScheduledAppointment> findAllByPatientAndCanceledByUtenteAndCanceledByHospital(Patient patient, boolean canceledByUtente, boolean canceledByHospital);

    List<ScheduledAppointment> findAllByCanceledByUtenteAndCanceledByHospital(boolean canceledByUtente, boolean canceledByHospital);

    List<ScheduledAppointment> findAllByPatientAndIsNowAnAppointment(Patient patient, boolean isNowAnAppointment);

    List<ScheduledAppointment> findAllByCanceledByUtenteAndCanceledByHospitalAndTicket(boolean canceledByUtente, boolean canceledByHospital, Ticket ticket);

    List<ScheduledAppointment> findAllByIsNowAnAppointment(boolean isNowAnAppointment);
}

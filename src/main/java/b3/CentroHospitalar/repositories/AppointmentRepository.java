package b3.CentroHospitalar.repositories;

import b3.CentroHospitalar.models.Appointment;
import b3.CentroHospitalar.models.users.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    List<Appointment> findAllByPatient(Patient patient);

    Optional<Appointment> findById(Long id);
}

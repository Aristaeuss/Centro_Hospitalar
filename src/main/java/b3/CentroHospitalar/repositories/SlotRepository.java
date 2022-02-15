package b3.CentroHospitalar.repositories;

import b3.CentroHospitalar.models.ScheduledAppointment;
import b3.CentroHospitalar.models.Slot;
import b3.CentroHospitalar.models.users.Doctor;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.print.Doc;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SlotRepository extends JpaRepository<Slot,Long> {



    Optional<Slot> findById(Long id);
    @Override
    <S extends Slot> List<S> findAll(Example<S> example);

    List<Slot> findAllBySpecialityNameAndScheduledAppointment(String speciality, ScheduledAppointment scheduledAppointment);

    List<Slot> findAllBySpecialityNameAndScheduledAppointmentAndDate(String specialityName, ScheduledAppointment scheduledAppointment, LocalDate date);

    List<Slot> findAllByDoctorAndScheduledAppointment(Doctor doctor, ScheduledAppointment scheduledAppointment);

    List<Slot> findAllByDoctorAndScheduledAppointmentAndDate(Doctor doctor, ScheduledAppointment scheduledAppointment, LocalDate localDate);

    List<Slot> findAllByDoctorAndDateAndScheduledAppointment(Doctor doctor, LocalDate date, ScheduledAppointment scheduledAppointment);

    List<Slot> findAllByDoctorAndDateTime(Doctor doctor, LocalDate date);

    List<Slot> findAllByDoctorAndDate(Doctor doctor, LocalDate date);
}

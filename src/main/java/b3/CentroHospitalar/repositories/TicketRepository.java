package b3.CentroHospitalar.repositories;

import b3.CentroHospitalar.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {

    Ticket findById(long id);

    List<Ticket> findAllByCalled(boolean called);

    List<Ticket> findAllByCalledAndAppointmentFinished(boolean called, boolean appointmentFinished);

    List<Ticket> findAllByRejectedForLatenessAndAppointmentFinished(boolean rejectedForLateness, boolean appointmentFinished);

    List<Ticket> findAllByCalledAndAppointmentFinishedAndRejectedForLateness(boolean called, boolean appointmentFinished, boolean rejectedForLateness);
}

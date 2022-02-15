package b3.CentroHospitalar.services;

import b3.CentroHospitalar.models.Appointment;
import b3.CentroHospitalar.models.Ticket;
import b3.CentroHospitalar.models.users.Doctor;
import b3.CentroHospitalar.models.users.Patient;
import b3.CentroHospitalar.models.users.User;
import b3.CentroHospitalar.services.Exceptions.InexistentUserException;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface TicketService {


    Ticket createTicket(int nif) throws InexistentUserException;

    Ticket findTicketById(String ticketId);

    Ticket seeNextTicketByDoctor(Doctor doctor);

    void getNextTicketByDoctor(ModelAndView mav, Doctor doctor);

    void markTicketAsCalled(Ticket ticket);

    List<Appointment> getLastAppointmentsFromPatient(Ticket ticket, int numberOfAppointments);

    List<Ticket> getLastEightCalledTickets();

    void callTicket(Doctor doctor, ModelMap map, Ticket ticket);

    void getNextTicketByDoctorThatIsNotThisOne(ModelAndView mav, Doctor loggedUser, Ticket ticket);

    void rejectLateTicket(Ticket ticket);

    List<Ticket> getTicketsWaitingByDoctor(Doctor doctor);

    void callTicketAgain(Ticket ticket);

    void patientAbsent(Ticket ticket);

    boolean didTicketMissTheAppointment(Ticket ticket);

    List<Ticket> getTicketsWaiting();

    List<Ticket> getTicketsToday(List<Ticket> ticketsWaiting);

    Ticket getNextTicketFor(Patient patient);
}

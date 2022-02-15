package b3.CentroHospitalar.services;

import b3.CentroHospitalar.models.Appointment;
import b3.CentroHospitalar.models.ScheduledAppointment;
import b3.CentroHospitalar.models.Slot;
import b3.CentroHospitalar.models.Ticket;
import b3.CentroHospitalar.models.users.Doctor;
import b3.CentroHospitalar.models.users.Patient;
import b3.CentroHospitalar.repositories.*;
import b3.CentroHospitalar.services.Exceptions.IncorrectUserTypeException;
import b3.CentroHospitalar.services.Exceptions.InexistentUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import javax.print.Doc;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService{
    @Autowired
    UserService userService;
    @Autowired
    ScheduledAppointmentService scheduledAppointmentService;
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    ScheduledAppointmentRepository scheduledAppointmentRepository;
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    DoctorRepository doctorRepository;

    @Override
    public Ticket createTicket(int nif) throws InexistentUserException {
        Patient patient=userService.findPatientByNif(nif);
        if(patient==null){
            throw new InexistentUserException("No user with NIF:"+nif);
        }
        ScheduledAppointment scheduledAppointment=null;
        try {
            scheduledAppointment=scheduledAppointmentService.todaysNextNonCanceledNotAppointmentYetScheduledAppointmentOf(patient);
            if(scheduledAppointment==null||!scheduledAppointment.getSlot().getDate().equals(LocalDate.now())){
                return null;
            }
        } catch (IncorrectUserTypeException e) {
            e.printStackTrace();
        }
        Ticket ticket= new Ticket(scheduledAppointment);
        ticketRepository.save(ticket);
        scheduledAppointment.setTicket(ticket);
        scheduledAppointmentRepository.save(scheduledAppointment);
        return ticket;
    }

    @Override
    public Ticket findTicketById(String ticketId) {
        long id= Long.parseLong(ticketId);
        return ticketRepository.findById(id);
    }

    @Override
    public Ticket seeNextTicketByDoctor(Doctor doctor){
        List<Ticket> ticketList=ticketRepository.findAllByCalled(false);
        Ticket ticket=null;
        for(Ticket tkt:ticketList){
            if(tkt.getScheduledAppointment().getSlot().getDoctor().getOrderNumber()==doctor.getOrderNumber()){
                if(ticket==null || tkt.getScheduledAppointment().getSlot().getDateTime().isBefore(ticket.getScheduledAppointment().getSlot().getDateTime())){
                    ticket=tkt;
                }
            }
        }
        return ticket;
    }

    @Override
    public void getNextTicketByDoctor(ModelAndView mav, Doctor doctor){
        Ticket ticket=doctor.getAppointmentTicketInProgress();
        if(ticket!=null){
            mav.addObject("ticket",ticket);
            mav.addObject("patientsAppointments",getLastAppointmentsFromPatient(ticket,3));
            return;
        }
        List<Ticket> ticketList=ticketRepository.findAllByCalled(false);
        for(Ticket tkt:ticketList){
            if(tkt.getScheduledAppointment().getSlot().getDoctor().getOrderNumber()==doctor.getOrderNumber()){
                if(ticket==null || tkt.getScheduledAppointment().getSlot().getDateTime().isBefore(ticket.getScheduledAppointment().getSlot().getDateTime())){
                        ticket=tkt;
                }
            }
        }
        mav.addObject("ticket",ticket);
        if(didTicketMissTheAppointment(ticket)){
            mav.addObject("lateTicket",true);
        }else{
            if(ticket==null){
                mav.addObject("noTickets",true);
                return;
            }
            ticket.setCalled(true);
            ticket.setCalledTime(LocalDateTime.now());
            ticketRepository.save(ticket);
            doctor.setAppointmentTicketInProgress(ticket);
            doctorRepository.save(doctor);
            ScheduledAppointment scheduledAppointment=ticket.getScheduledAppointment();
            scheduledAppointment.setNowAnAppointment(true);
            scheduledAppointmentRepository.save(scheduledAppointment);
            mav.addObject("patientsAppointments",getLastAppointmentsFromPatient(ticket,3));
        }
    }

    @Override
    public void markTicketAsCalled(Ticket ticket){
        ticket.setCalled(true);
        ticket.setCalledTime(LocalDateTime.now());
        ticketRepository.save(ticket);
        ScheduledAppointment scheduledAppointment=ticket.getScheduledAppointment();
        Doctor doctor = scheduledAppointment.getSlot().getDoctor();
        doctor.setAppointmentTicketInProgress(ticket);
        doctorRepository.save(doctor);
        scheduledAppointment.setNowAnAppointment(true);
        scheduledAppointmentRepository.save(scheduledAppointment);
    }

    @Override
    public List<Appointment> getLastAppointmentsFromPatient(Ticket ticket, int numberOfAppointments) {
        Patient patient=ticket.getScheduledAppointment().getPatient();
        List<Appointment> appointments=appointmentRepository.findAllByPatient(patient);
        appointments.sort(new Comparator<Appointment>() {
            @Override
            public int compare(Appointment o1, Appointment o2) {
                return o1.getStartTime().compareTo(o2.getStartTime());
            }
        });
        int i=0;
        List<Appointment> lastAppointments=new ArrayList<>();
        for(Appointment appointment:appointments){
            lastAppointments.add(appointment);
            i++;
            if(i==numberOfAppointments){
                break;
            }
        }
        return lastAppointments;
    }

    @Override
    public List<Ticket> getLastEightCalledTickets() {
        List<Ticket> tickets=new ArrayList<>();
        List<Ticket> allCalledTickets=ticketRepository.findAllByCalledAndAppointmentFinishedAndRejectedForLateness(true,false,false);
        allCalledTickets.sort(new Comparator<Ticket>() {
            @Override
            public int compare(Ticket o1, Ticket o2) {
                return o1.getCalledTime().compareTo(o2.getCalledTime());
            }
        });
        int i=0;
        for(Ticket ticket:allCalledTickets){
            tickets.add(ticket);
            i++;
            if(i==8){
                break;
            }
        }
        return tickets;
    }

    @Override
    public void callTicket(Doctor doctor, ModelMap map, Ticket ticket) {
        Ticket ticketDoctor=doctor.getAppointmentTicketInProgress();
        if(ticketDoctor!=null){
            map.put("ticket",ticketDoctor);
            map.put("ticketMessage",true);
            map.put("patientsAppointments",getLastAppointmentsFromPatient(ticketDoctor,3));
            return;
        }
        map.put("ticket",ticket);
        markTicketAsCalled(ticket);
        map.put("patientsAppointments",getLastAppointmentsFromPatient(ticket,3));
    }

    @Override
    public void getNextTicketByDoctorThatIsNotThisOne(ModelAndView mav, Doctor loggedUser, Ticket tick) {
        List<Ticket> ticketList=ticketRepository.findAllByCalled(false);
        Ticket ticket=null;
        for(Ticket tkt:ticketList){
            if(tkt.getScheduledAppointment().getSlot().getDoctor().getOrderNumber()==loggedUser.getOrderNumber()){
                if(ticket==null || tkt.getScheduledAppointment().getSlot().getDateTime().isBefore(ticket.getScheduledAppointment().getSlot().getDateTime())){
                    if(tkt!=tick){
                    ticket=tkt;}
                }
            }
        }
        mav.addObject("ticket",ticket);
        if(didTicketMissTheAppointment(ticket)){
            mav.addObject("lateTicket",true);
        }else{
            if(ticket==null){
                mav.addObject("noTickets",true);
                return;
            }
            ticket.setCalled(true);
            ticket.setCalledTime(LocalDateTime.now());
            ticketRepository.save(ticket);
            ScheduledAppointment scheduledAppointment=ticket.getScheduledAppointment();
            scheduledAppointment.setNowAnAppointment(true);
            scheduledAppointmentRepository.save(scheduledAppointment);
        }
    }

    @Override
    public void rejectLateTicket(Ticket ticket) {
        ticket.setRejectedForLateness(true);
        ticket.setRejectedDateTime(LocalDateTime.now());
        ScheduledAppointment scheduledAppointment = ticket.getScheduledAppointment();
        scheduledAppointment.setCanceledByHospital(true);
        scheduledAppointmentRepository.save(scheduledAppointment);
        ticket.setCalled(true);
        ticket.setCalledTime(LocalDateTime.now());
        ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> getTicketsWaitingByDoctor(Doctor doctor) {
        List<Ticket> tickets=ticketRepository.findAllByCalled(false);
        List<Ticket> ticketsWaiting=new ArrayList<>();
        for(Ticket ticket:tickets){
            if(ticket.getScheduledAppointment().getSlot().getDoctor().getOrderNumber()==doctor.getOrderNumber()){
                ticketsWaiting.add(ticket);
            }
        }
        return ticketsWaiting;
    }

    @Override
    public void callTicketAgain(Ticket ticket) {
        ticket.setCalledTime(LocalDateTime.now());
        ticketRepository.save(ticket);
    }

    @Override
    public void patientAbsent(Ticket ticket) {
        ticket.setRejectedForLateness(true);
        ticket.setRejectedDateTime(LocalDateTime.now());
        ticketRepository.save(ticket);
        Doctor doctor =ticket.getScheduledAppointment().getSlot().getDoctor();
        doctor.setAppointmentTicketInProgress(null);
        doctorRepository.save(doctor);
    }

    public boolean isTicketLateForAppointment(Ticket ticket){
        return ticket.getScheduledAppointment().getSlot().getDateTime().isBefore(ticket.getCheckInDateTime());
    }

    @Override
    public boolean didTicketMissTheAppointment(Ticket ticket){
        if (ticket != null) {
            return ticket.getScheduledAppointment().getSlot().getDateTime().plusMinutes(Slot.MAX_NUM_MINUTES_LATE).isBefore(ticket.getCheckInDateTime());
        }
        return false;
    }

    @Override
    public List<Ticket> getTicketsWaiting() {
        return ticketRepository.findAllByCalled(false);
    }

    @Override
    public List<Ticket> getTicketsToday(List<Ticket> ticketsWaiting) {
        List<Ticket> ticketsForToday=new ArrayList<>();
        for(Ticket ticket:ticketsWaiting){
            if(ticket.getCheckInDateTime().toLocalDate().equals(LocalDate.now())){
                ticketsForToday.add(ticket);
            }
        }
        return ticketsForToday;
    }

    @Override
    public Ticket getNextTicketFor(Patient patient) {
        List<Ticket> unfilteredTickets = ticketRepository.findAllByRejectedForLatenessAndAppointmentFinished(false, false);
        List<Ticket> filteredTickets = new ArrayList<>();
        for (Ticket ticket : unfilteredTickets) {
            if (ticket.getScheduledAppointment().getPatient().getNif() == patient.getNif()) {
                filteredTickets.add(ticket);
            }
        }
        return filteredTickets.isEmpty() ? null : filteredTickets.get(0);
    }
}

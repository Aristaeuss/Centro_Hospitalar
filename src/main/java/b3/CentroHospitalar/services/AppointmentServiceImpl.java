package b3.CentroHospitalar.services;

import b3.CentroHospitalar.models.Appointment;
import b3.CentroHospitalar.models.Invoice;
import b3.CentroHospitalar.models.ScheduledAppointment;
import b3.CentroHospitalar.models.Ticket;
import b3.CentroHospitalar.models.users.Doctor;
import b3.CentroHospitalar.models.users.Patient;
import b3.CentroHospitalar.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    ScheduledAppointmentRepository scheduledAppointmentRepository;
    @Autowired
    InvoiceService invoiceService;
    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    TicketRepository ticketRepository;

    @Override
    public void officializeAppointment(Appointment appointment, ScheduledAppointment scheduledAppointment) {
        Appointment appt=new Appointment(scheduledAppointment);
        scheduledAppointment.setNowAnAppointment(true);
        scheduledAppointment.setAppointment(appt);
        appt.setStartTime(scheduledAppointment.getTicket().getCalledTime());
        appt.closeAppointment(appointment.getTitle(),appointment.getNote(),appointment.getPrescription());
        appointmentRepository.save(appt);
        scheduledAppointmentRepository.save(scheduledAppointment);
        Ticket ticket = scheduledAppointment.getTicket();
        ticket.setAppointmentFinished(true);
        ticketRepository.save(ticket);
        Doctor doctor=appt.getDoctor();
        doctor.setAppointmentTicketInProgress(null);
        doctorRepository.save(doctor);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Invoice invoice = invoiceService.createInvoice(scheduledAppointment.getPatient(), Invoice.DEFAULT_APPOINTMENT_PRICE);
                invoiceRepository.save(invoice);
                appt.setInvoice(invoice);
                invoice.setAppointment(appt);
                appointmentRepository.save(appt);
                invoiceRepository.save(invoice);
            }
        }).start();
    }

    @Override
    public List<Appointment> findAllByPatient(Patient loggedUser) {
        return appointmentRepository.findAllByPatient(loggedUser);
    }

    @Override
    public Optional<Appointment> findById(long parseLong) {
        return appointmentRepository.findById(parseLong);
    }
}

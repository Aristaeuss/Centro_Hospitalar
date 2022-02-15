package b3.CentroHospitalar.services;

import b3.CentroHospitalar.models.ScheduledAppointment;
import b3.CentroHospitalar.models.Slot;
import b3.CentroHospitalar.models.users.Doctor;
import b3.CentroHospitalar.models.users.Patient;
import b3.CentroHospitalar.models.users.User;
import b3.CentroHospitalar.repositories.DoctorRepository;
import b3.CentroHospitalar.repositories.PatientRepository;
import b3.CentroHospitalar.repositories.ScheduledAppointmentRepository;
import b3.CentroHospitalar.repositories.SlotRepository;
import b3.CentroHospitalar.services.Exceptions.IncorrectUserTypeException;
import b3.CentroHospitalar.services.Exceptions.ScheduledAppointmentAlreadyCanceledException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ScheduledAppointmentServiceImpl implements ScheduledAppointmentService {

    @Autowired
    PatientRepository patientRepository;
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    ScheduledAppointmentRepository scheduledAppointmentRepository;
    @Autowired
    SlotRepository slotRepository;

    /**
     * Returns the next scheduled appointment, chronologically, of the given User.
     */
    @Override
    public ScheduledAppointment nextNonCanceledNotAppointmentYetScheduledAppointmentOf(User user) throws IncorrectUserTypeException {
        List<ScheduledAppointment> appointments = allNonCanceledNotYetHappenedAppointmentsOf(user);
        List<ScheduledAppointment> scheduledAppointments=new ArrayList<>();
        for(ScheduledAppointment scheduledAppointment: appointments){
            if(scheduledAppointment.getTicket()==null){
                scheduledAppointments.add(scheduledAppointment);
            }
        }
        if (scheduledAppointments.isEmpty())
            return null;
        return scheduledAppointments.get(0);
    }

    /**
     * Returns the next scheduled appointment of today, chronologically, of the given User.
     */
    @Override
    public ScheduledAppointment todaysNextNonCanceledNotAppointmentYetScheduledAppointmentOf(User user) throws IncorrectUserTypeException {
        List<ScheduledAppointment> appointments = allNonCanceledNotYetHappenedAppointmentsOf(user);
        List<ScheduledAppointment> scheduledAppointments=new ArrayList<>();
        for(ScheduledAppointment scheduledAppointment: appointments){
            if(scheduledAppointment.getTicket()==null && scheduledAppointment.getSlot().getDate().equals(LocalDate.now())){
                scheduledAppointments.add(scheduledAppointment);
            }
        }
        if (scheduledAppointments.isEmpty())
            return null;
        return scheduledAppointments.get(0);
    }

    /**
     * Returns a chronologically ordered list of scheduled appointments of the given User that have not yet happened.
     */
    @Override
    public List<ScheduledAppointment> allNonCanceledNotYetHappenedAppointmentsOf(User user) throws IncorrectUserTypeException {
        List<ScheduledAppointment> appointments = allNonCanceledAppointmentsOf(user);
        List<ScheduledAppointment> filteredAppointments = new ArrayList<>();
        for (ScheduledAppointment appointment : appointments) {
            if (!appointment.isNowAnAppointment()) {
                filteredAppointments.add(appointment);
            }
        }
        return filteredAppointments;
    }


    /**
     * Returns a chronologically ordered list of scheduled appointments of the given User.
     */
    @Override
    public List<ScheduledAppointment> allNonCanceledAppointmentsOf(User user) throws IncorrectUserTypeException {
        Patient patient = patientRepository.findByNif(user.getNif());
        Doctor doctor = doctorRepository.findByNif(user.getNif());
        if(patient == null && doctor == null){
            throw new IncorrectUserTypeException("Only Patients and Doctors have scheduled appointments");
        }
        if (patient != null) {
            List<ScheduledAppointment> appointments =
                    scheduledAppointmentRepository.findAllByPatientAndCanceledByUtenteAndCanceledByHospital(patient, false, false);
            appointments.sort(new ScheduledAppointmentChronologicalComparator());
            return appointments;
        } else {
            List<ScheduledAppointment> allAppointments =
                    scheduledAppointmentRepository.findAllByCanceledByUtenteAndCanceledByHospital(false, false);
            List<ScheduledAppointment> doctorsAppointments = new ArrayList<>();
            for (ScheduledAppointment a : allAppointments) {
                if (a.getSlot().getDoctor().getNif() == doctor.getNif() && !a.isNowAnAppointment()) {
                    doctorsAppointments.add(a);
                }
            }
            doctorsAppointments.sort(new ScheduledAppointmentChronologicalComparator());
            return doctorsAppointments;
        }
    }

    @Override
    public ScheduledAppointment findById(Long scheduledAppointmentId) {
        Optional<ScheduledAppointment> optional = scheduledAppointmentRepository.findById(scheduledAppointmentId);
        return optional.orElse(null);
    }

    @Override
    public void cancel(ScheduledAppointment scheduledAppointment, boolean byHospitalNotUser) throws ScheduledAppointmentAlreadyCanceledException {
        if (scheduledAppointment == null)
            return;
        if (scheduledAppointment.isCanceledByUtente() || scheduledAppointment.isCanceledByHospital()) {
            throw new ScheduledAppointmentAlreadyCanceledException("The appointment was already canceled.");
        }
        if (byHospitalNotUser) {
            scheduledAppointment.setCanceledByHospital(true);
        } else {
            scheduledAppointment.setCanceledByUtente(true);
        }
        Slot slot = scheduledAppointment.getSlot();
        slot.setScheduledAppointment(null);
        scheduledAppointmentRepository.save(scheduledAppointment);
        slotRepository.save(slot);
    }

    @Override
    public List<ScheduledAppointment> allScheduledAppointmentsForDoctorByDateNotYetAppointment(Doctor doctor, LocalDate date) {
        List<Slot> slots=slotRepository.findAllByDoctorAndDate(doctor,date);
        List<ScheduledAppointment> scheduledAppointments= new ArrayList<>();
        for(Slot slot:slots){
            if(slot.getScheduledAppointment()!=null && !slot.getScheduledAppointment().isCanceledByHospital()
            && !slot.getScheduledAppointment().isCanceledByUtente() && !slot.getScheduledAppointment().isNowAnAppointment()){
                scheduledAppointments.add(slot.getScheduledAppointment());
            }
        }
        return scheduledAppointments;
    }

    @Override
    public List<ScheduledAppointment> allScheduledAppointmentsForDoctorByDate(Doctor doctor, LocalDate date) {
        List<Slot> slots=slotRepository.findAllByDoctorAndDate(doctor,date);
        List<ScheduledAppointment> scheduledAppointments= new ArrayList<>();
        for(Slot slot:slots){
            if(slot.getScheduledAppointment()!=null && !slot.getScheduledAppointment().isCanceledByHospital()
                    && !slot.getScheduledAppointment().isCanceledByUtente()){
                scheduledAppointments.add(slot.getScheduledAppointment());
            }
        }
        return scheduledAppointments;
    }

    @Override
    public void makeItAnAppointment(ScheduledAppointment scheduledAppointment) {
        scheduledAppointment.setNowAnAppointment(true);
        scheduledAppointmentRepository.save(scheduledAppointment);
    }

    @Override
    public ScheduledAppointment nextAppointmentForDoctor(Doctor doctor) {
        List<Slot> slots = slotRepository.findAllByDoctorAndDate(doctor,LocalDate.now());
        List<ScheduledAppointment> scheduledAppointments=new ArrayList<>();
        for(Slot slot:slots){
            if(slot.getScheduledAppointment()!=null && !slot.getScheduledAppointment().isNowAnAppointment()
            && !slot.getScheduledAppointment().isCanceledByUtente() && !slot.getScheduledAppointment().isCanceledByHospital()){
                scheduledAppointments.add(slot.getScheduledAppointment());
            }
        }
        scheduledAppointments.sort(new ScheduledAppointmentChronologicalComparator());
        if(scheduledAppointments.isEmpty()){
            return null;
        }
        return scheduledAppointments.get(0);
    }

    @Override
    public List<ScheduledAppointment> allLateNotCheckedInScheduledAppointments() {
        List<ScheduledAppointment> scheduledAppointments=scheduledAppointmentRepository.findAllByCanceledByUtenteAndCanceledByHospitalAndTicket(false,false,null);
        List<ScheduledAppointment> lateScheduledAppointments=new ArrayList<>();
        for(ScheduledAppointment scheduledAppointment:scheduledAppointments){
            if(scheduledAppointment.getSlot().getDate().equals(LocalDate.now()) && scheduledAppointment.getSlot().getDateTime().isBefore(LocalDateTime.now())){
                lateScheduledAppointments.add(scheduledAppointment);
            }
        }
        return lateScheduledAppointments;
    }

    @Override
    public List<ScheduledAppointment> allCanceledAppointmentsOf(User loggedUser) {
        List<ScheduledAppointment> sa1= scheduledAppointmentRepository.findAllByCanceledByUtenteAndCanceledByHospital(true,false);
        List<ScheduledAppointment> sa2= scheduledAppointmentRepository.findAllByCanceledByUtenteAndCanceledByHospital(false,true);
        sa2.addAll(sa1);
        sa2.sort(new ScheduledAppointmentChronologicalComparator());
        return sa2;
    }

    @Override
    public List<ScheduledAppointment> allNotYetHappenedScheduledAppointmentsOf(User user) {
        List<ScheduledAppointment> appointments = scheduledAppointmentRepository.findAllByIsNowAnAppointment(false);
        List<ScheduledAppointment> appointmentsOfUser = new ArrayList<>();
        for (ScheduledAppointment appointment : appointments) {
            if (appointment.getPatient().getNif() == user.getNif()) {
                appointmentsOfUser.add(appointment);
            }
        }
        appointmentsOfUser.sort(new ScheduledAppointmentChronologicalComparator());
        return appointmentsOfUser;
    }

    public static class ScheduledAppointmentChronologicalComparator implements Comparator<ScheduledAppointment> {
        @Override
        public int compare(ScheduledAppointment a1, ScheduledAppointment a2) {
            return a1.getSlot().getDateTime().compareTo(a2.getSlot().getDateTime());
        }
    }
}

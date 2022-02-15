package b3.CentroHospitalar.controllers;

import b3.CentroHospitalar.models.Invoice;
import b3.CentroHospitalar.models.ScheduledAppointment;
import b3.CentroHospitalar.models.Slot;
import b3.CentroHospitalar.models.users.Doctor;
import b3.CentroHospitalar.models.users.Patient;
import b3.CentroHospitalar.models.users.Speciality;
import b3.CentroHospitalar.models.users.User;
import b3.CentroHospitalar.services.*;
import b3.CentroHospitalar.services.Exceptions.IncorrectUserTypeException;
import b3.CentroHospitalar.services.Exceptions.NoSuchSlotException;
import b3.CentroHospitalar.services.Exceptions.ScheduledAppointmentAlreadyCanceledException;
import b3.CentroHospitalar.services.Exceptions.SlotAlreadyBookedException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class UtenteController {

    @Autowired
    UserService userService;
    @Autowired
    SpecialityService specialityService;
    @Autowired
    UserImageService userImageService;
    @Autowired
    SlotService slotService;
    @Autowired
    ScheduledAppointmentService scheduledAppointmentService;
    @Autowired
    InvoiceService invoiceService;
    @Autowired
    AppointmentService appointmentService;
    @Autowired
    TicketService ticketService;

    private static final String U="loggedInUser";

    @GetMapping(value = "/calendarioUtente")
    public ModelAndView calendarioUtente(){
        ModelAndView mav=slotService.daysBeforeFilter();
        mav.addObject(U,userService.getLoggedUser());
        mav.addObject("specialities",specialityService.findAll());
        mav.addObject("doctorMessage", "Antes de escolher o médico escolha a sua especialidade");
        mav.addObject("specialityMessage","Escolha uma especialidade");
        mav.addObject("doctors",null);
        mav.setViewName("calendarUtente");
        return mav;
    }

    @PostMapping(value="/calendarioUtenteE")
    public ModelAndView calendarioUtenteE(@RequestParam String specialityName){
        ModelAndView mav= slotService.getAvailabilitiesBySpeciality(specialityName);
        mav.addObject(U,userService.getLoggedUser());
        mav.addObject("doctorMessage", "Escolha um(a) médico(a)");
        mav.addObject("specialityMessage",specialityName);
        Speciality speciality=specialityService.findByName(specialityName);
        mav.addObject("doctors",userService.findDoctorBySpeciality(speciality));
        LocalDate today= LocalDate.now();
        List<Doctor> doctorsAvailableTodayBySpeciality=userService.findDoctorsBySpecialityByDay(userService.findDoctorBySpeciality(speciality),specialityName,today);
        mav.addObject("doctorsAvailableForDay",doctorsAvailableTodayBySpeciality);
        mav.setViewName("calendarUtente");
        List<Slot> slotsForFirstDoctor= slotService.slotsAvailableForFirstDoctorAndDateThatHaveNotHappenedYet(doctorsAvailableTodayBySpeciality,today);
        mav.addObject("slotsForDoctor",slotsForFirstDoctor);
        mav.addObject("dateSelected",LocalDateTime.now());
        return mav;
    }

    @PostMapping(value="/calendarioUtenteM")
    public ModelAndView calendarioUtenteM(@RequestParam String doctorName, String speciality){
        if(doctorName.equals("none")){
            ModelAndView mav= slotService.getAvailabilitiesBySpeciality(speciality);
            mav.addObject(U,userService.getLoggedUser());
            mav.addObject("specialities",specialityService.findAll());
            mav.addObject("doctorMessage", "Antes de escolher o médico escolha a sua especialidade");
            mav.addObject("specialityMessage","speciality");
            List<Doctor> doctorsBySpeciality=userService.findDoctorBySpeciality(specialityService.findByName(speciality));
            mav.addObject("doctors",doctorsBySpeciality);
            LocalDate today= LocalDate.now();

            mav.addObject("doctorsAvailableForDay",userService.findDoctorsBySpecialityByDay(doctorsBySpeciality,speciality,today));
            Speciality sp=specialityService.findByName(speciality);
            List<Slot> slotsForFirstDoctor= slotService.slotsAvailableForFirstDoctorAndDateThatHaveNotHappenedYet(
                    userService.findDoctorsBySpecialityByDay(userService.findDoctorBySpeciality(sp),speciality,today),today);
            mav.addObject("slotsForDoctor",slotsForFirstDoctor);
            mav.addObject("dateSelected", LocalDate.now());
            mav.setViewName("calendarUtenteSemPopUp");
            return mav;
        }
        int orderNumber=Integer.parseInt(doctorName);
        ModelAndView mav= slotService.getAvailabilitiesByDoctor(orderNumber);
        mav.addObject(U,userService.getLoggedUser());
        mav.addObject("doctorMessage", "Escolha um(a) médico(a)");

        List<Slot> slotsForDoctorForDate=slotService.getSlotsForDoctorAndDateThatHaveNotHappenedYet(userService.findDoctorByOrderNumber(orderNumber),LocalDate.now());
        mav.addObject("slotsForDoctor", slotsForDoctorForDate);
        mav.addObject("dateSelected", LocalDate.now());
        mav.setViewName("calendarUtenteSemPopUp");
        return mav;
    }

    @GetMapping(value="/calendarioUtente/{dayMonthYear}")
    public ModelAndView chooseDay(@PathVariable(name="dayMonthYear") String dayMonthYearString){
        String[] split=dayMonthYearString.split(",");
        ModelAndView mav;
        if(split.length>4){
            mav=slotService.checkSlotsAvailableByDoctorAndDate(dayMonthYearString);
        }else {
            mav = slotService.checkDoctorsAvailableByDateAndSpeciality(dayMonthYearString);
        }
        mav.addObject(U,userService.getLoggedUser());
        mav.setViewName("calendarUtenteSemPopUp");
        return mav;
    }

    @GetMapping(value="/calendarioUtenteL/{dateDoctor}")
    public ModelAndView chooseDayDoctorList(@PathVariable(name="dateDoctor") String dateDoctor){
        ModelAndView mav=slotService.checkSlotsAvailableByDoctorAndDate(dateDoctor);
        mav.addObject(U,userService.getLoggedUser());
        mav.setViewName("calendarUtenteSemPopUp");
        return mav;
    }

    @GetMapping (value = "/consultaMarcada/{slotId}")
    public String consultaMarcada(ModelMap map,@PathVariable(name="slotId") String slotID) {
        User user=userService.getLoggedUser();
        map.put(U,user);
        ScheduledAppointment scheduledAppointment;
        try {
            Long slot = Long.parseLong(slotID);
            scheduledAppointment=slotService.bookSlot(slot, user);
        }catch (IncorrectUserTypeException | NoSuchSlotException e){
            System.out.println(e.getMessage());
            return "redirect:/landingPage";
        } catch (SlotAlreadyBookedException e) {
            map.put("errorMessage", "A vaga selecionada ja foi occupada");
            return "consultaMarcada";
        }
        map.put("scheduledAppointment",scheduledAppointment);
        return "consultaMarcada";
    }


    @GetMapping(value = "/VistaGeralUtente")
    public String vgUtente(ModelMap map){
        User user = userService.getLoggedUser();
        map.put(U, user);
        try {
            map.put("nextAppointment", scheduledAppointmentService.todaysNextNonCanceledNotAppointmentYetScheduledAppointmentOf(user));
            map.put("numAppointments", scheduledAppointmentService.allNonCanceledNotYetHappenedAppointmentsOf(user).size());
        } catch (IncorrectUserTypeException e) {
            System.out.println(e.getMessage());
            return "redirect:/landingPage";
        }
        map.put("mostUrgentInvoice", invoiceService.getMostUrgentInvoiceFor((Patient)user));
        map.put("pendingInvoices", invoiceService.getPendingInvoicesFor((Patient) user));
        map.put("nextTicket", ticketService.getNextTicketFor((Patient) user));
        return "VistaGeralUtente";
    }


    @GetMapping(value = "/consultasUtente")
    public String consultas(ModelMap map) {
        User loggedUser = userService.getLoggedUser();
        try {
            map.put("nextAppointment", scheduledAppointmentService.nextNonCanceledNotAppointmentYetScheduledAppointmentOf(loggedUser));
            map.put("numAppointments", scheduledAppointmentService.allNonCanceledNotYetHappenedAppointmentsOf(loggedUser).size());
            map.put("canceledAppointments",scheduledAppointmentService.allCanceledAppointmentsOf(userService.getLoggedUser()));
            map.put("previousAppointments",appointmentService.findAllByPatient((Patient)userService.getLoggedUser()));
        } catch (IncorrectUserTypeException e) {
            System.out.println(e.getMessage());
            return "redirect:/landingPage";
        }
        map.put(U, loggedUser);
        return "consultasUtente";
    }

    @GetMapping(value = "/listaConsultasCanceladasUtente")
    public String listaConsultasCanceladas(ModelMap map){
        map.put(U, userService.getLoggedUser());
        map.put("canceledAppointments",scheduledAppointmentService.allCanceledAppointmentsOf(userService.getLoggedUser()));
        return "listaConsultasUtente";
    }

    @GetMapping(value="/listaConsultasPreviasUtente")
    public String listaConsultasPrevias(ModelMap map){
        map.put(U, userService.getLoggedUser());
        map.put("previousAppointments",appointmentService.findAllByPatient((Patient)userService.getLoggedUser()));
        return "listaConsultasUtente";
    }

    @GetMapping(value = "/listaConsultasUtente")
    public String listaConsultas(ModelMap map) {
        User loggedUser = userService.getLoggedUser();
        try {
            map.put("scheduledAppointments", scheduledAppointmentService.allNonCanceledNotYetHappenedAppointmentsOf(loggedUser));
        } catch (IncorrectUserTypeException e) {
            System.out.println(e.getMessage());
            return "redirect:/landingPage";
        }
        map.put(U, loggedUser);
        return "listaConsultasUtente";
    }

    @GetMapping(value = "/listaFaturasUtente")
    public String listaFaturas(ModelMap map) {
        User loggedUser = userService.getLoggedUser();
        map.put("invoices", invoiceService.getInvoicesFor((Patient) loggedUser));
        map.put(U, loggedUser);
        return "listaFaturasUtente";
    }

    @GetMapping(value = "/listaFaturasUtente/{filter}")
    public String listaFaturasFiltradas(ModelMap map, @PathVariable String filter) {
        User loggedUser = userService.getLoggedUser();
        List<Invoice> invoices;
        switch (filter) {
            case "todas":
                invoices = invoiceService.getInvoicesFor((Patient) loggedUser); break;
            case "pendentes":
                invoices = invoiceService.getPendingInvoicesFor((Patient) loggedUser); break;
            case "pagas":
                invoices = invoiceService.getPaidInvoicesFor((Patient) loggedUser); break;
            default:
                invoices = null; break;
        }
        map.put("invoices", invoices);
        map.put(U, loggedUser);
        return "listaFaturasUtente";
    }

    @GetMapping(value = "/cancelarConsultaUtente/{scheduledAppointmentId}")
    public String consultaCancelada(ModelMap map, @PathVariable(name="scheduledAppointmentId") String scheduledAppointmentIdString) {
        User user=userService.getLoggedUser();
        Long scheduledAppointmentId = Long.parseLong(scheduledAppointmentIdString);
        ScheduledAppointment scheduledAppointment = scheduledAppointmentService.findById(scheduledAppointmentId);
        if (scheduledAppointment == null || user.getNif() != scheduledAppointment.getPatient().getNif()) {
            return "redirect:/landingPage";
        }
        try {
            scheduledAppointmentService.cancel(scheduledAppointment, false);
        } catch (ScheduledAppointmentAlreadyCanceledException e) {
            System.out.println(e.getMessage());
        }
        map.put(U,user);
        map.put("scheduledAppointment",scheduledAppointment);
        map.put("canceledByEmployee", false);
        return "consultaCancelada";
    }

    @GetMapping(value = "/remarcarConsultaUtente/{scheduledAppointmentToCancelId}/{slotId}")
    public String consultaRemarcada(ModelMap map, @PathVariable(name="scheduledAppointmentToCancelId") String scheduledAppointmentToCancelIdString,
                                                    @PathVariable(name="slotId") String slotID) {
        User user=userService.getLoggedUser();
        map.put(U,user);
        Long scheduledAppointmentId = Long.parseLong(scheduledAppointmentToCancelIdString);
        ScheduledAppointment scheduledAppointmentToCancel = scheduledAppointmentService.findById(scheduledAppointmentId);
        if (scheduledAppointmentToCancel == null || user.getNif() != scheduledAppointmentToCancel.getPatient().getNif()) {
            return "redirect:/landingPage";
        }
        ScheduledAppointment newlyBookedScheduledAppointment;
        try {
            Long slot = Long.parseLong(slotID);
            newlyBookedScheduledAppointment=slotService.bookSlot(slot, user);
        }catch (IncorrectUserTypeException | NoSuchSlotException e){
            System.out.println(e.getMessage());
            return "redirect:/landingPage";
        } catch (SlotAlreadyBookedException e) {
            map.put("errorMessage", "A vaga selecionada ja foi occupada");
            return "consultaRemarcada";
        }
        map.put("newlyBookedScheduledAppointment", newlyBookedScheduledAppointment);
        try {
            scheduledAppointmentService.cancel(scheduledAppointmentToCancel, false);
        } catch (ScheduledAppointmentAlreadyCanceledException e) {
            System.out.println(e.getMessage());
        }
        map.put("canceledScheduledAppointment",scheduledAppointmentToCancel);
        return "consultaRemarcada";
    }

    @GetMapping(value = "/remarcarConsulta/{scheduledAppointmentId}")
    public ModelAndView remarcarConsulta(@PathVariable(name="scheduledAppointmentId") String scheduledAppointmentIdString) {
        User user=userService.getLoggedUser();
        Long scheduledAppointmentId = Long.parseLong(scheduledAppointmentIdString);
        ScheduledAppointment scheduledAppointment = scheduledAppointmentService.findById(scheduledAppointmentId);
        if (scheduledAppointment.getPatient().getNif() != user.getNif()) {
            return new ModelAndView("redirect:/landingPage");
        }
        Doctor doctor = scheduledAppointment.getSlot().getDoctor();
        ModelAndView mav= slotService.getAvailabilitiesByDoctor(doctor.getOrderNumber());
        mav.addObject(U, user);
        Gson gson = new Gson();
        mav.addObject("jsonScheduledAppointmentToCancelId", gson.toJson(scheduledAppointmentId));
        mav.addObject("scheduledAppointmentToCancel", scheduledAppointment);
        mav.setViewName("calendarUtenteSemPopUp");
        return mav;
    }

    @GetMapping(value = "/remarcarConsulta/{scheduledAppointmentId}/{dayMonthYear}")
    public ModelAndView remarcarConsultaChooseDay(@PathVariable(name="scheduledAppointmentId") String scheduledAppointmentIdString,
                                                  @PathVariable(name="dayMonthYear") String dayMonthYearString) {
        User user=userService.getLoggedUser();
        Long scheduledAppointmentId = Long.parseLong(scheduledAppointmentIdString);
        ScheduledAppointment scheduledAppointment = scheduledAppointmentService.findById(scheduledAppointmentId);
        if (scheduledAppointment.getPatient().getNif() != user.getNif()) {
            return new ModelAndView("redirect:/landingPage");
        }
        ModelAndView mav=slotService.checkDoctorsAvailableByDateAndSpeciality(dayMonthYearString);
        mav.addObject(U, user);
        Gson gson = new Gson();
        mav.addObject("jsonScheduledAppointmentToCancelId", gson.toJson(scheduledAppointmentId));
        mav.addObject("scheduledAppointmentToCancel", scheduledAppointment);
        mav.setViewName("calendarUtenteSemPopUp");
        return mav;
    }

    @GetMapping(value = "/medicosUtente")
    public String medicosUtente(ModelMap map){
        map.put(U, userService.getLoggedUser());
        return "medicosUtente";
    }

    @GetMapping(value = "/faturasUtente")
    public String faturas(ModelMap map){
        User user = userService.getLoggedUser();
        map.put(U, user);
        map.put("mostUrgentInvoice", invoiceService.getMostUrgentInvoiceFor((Patient)user));
        map.put("pendingInvoices", invoiceService.getPendingInvoicesFor((Patient) user));
        return "faturasUtente";
    }


    @GetMapping(value = "/perfilUtente")
    public String perfilUtente(ModelMap map){
        map.put(U, userService.getLoggedUser());
        return "perfilUtente";
    }

    @GetMapping(value = "/contactosUtente")
    public String contactosUtente(ModelMap map){
        map.put(U, userService.getLoggedUser());
        return "contactosUtente";
    }

    @GetMapping(value = "/infoUtente")
    public String infoUtente(ModelMap map){
        map.put(U, userService.getLoggedUser());
        return "infoUtente";
    }

    @PostMapping(value = "/perfilUtente")
    public String uploadImage(@RequestParam("file") MultipartFile file, ModelMap map) {
        User loggedUser = userService.getLoggedUser();
        map.put(U, loggedUser);
        if (!(loggedUser instanceof Patient))
            return "perfilUtente";
        userImageService.updateImageFor(loggedUser, file);
        return "perfilUtente";
    }

    @PostMapping(value="/alterarMoradaU")
    public String alterarMoradaU(ModelMap map,
                                @RequestParam String address,
                                @RequestParam String door,
                                @RequestParam String floor,
                                @RequestParam String postalCode,
                                @RequestParam String city){
        User user= userService.getLoggedUser();
        userService.updateAddress(user, address, door, floor, postalCode, city);
        map.put(U, user);
        return "perfilUtente";
    }

    @PostMapping(value="/alterarTelefoneU")
    public String alterarTelefoneU(ModelMap map, @RequestParam String telephone){
        User user= userService.getLoggedUser();
        userService.updateTelephone(user,telephone);
        map.put(U,user);
        return "perfilUtente";
    }

    @PostMapping(value="/alterarImagemU")
    public String alterarImagemU(ModelMap map, @RequestParam MultipartFile file){
        User user= userService.getLoggedUser();
        userImageService.updateImageFor(user,file);
        map.put(U,user);
        return "perfilUtente";
    }

    @PostMapping(value="/alterarNomeU")
    public String alterarPreferredNameU(ModelMap map, @RequestParam String name){
        User user=userService.getLoggedUser();
        userService.updatePreferredName(user,name);
        map.put(U,user);
        return "perfilUtente";
    }

    @GetMapping(value = {"/userListMedicos"})
    public String listMedicos(ModelMap map) {
        User user=userService.getLoggedUser();
        map.put("doctors", userService.getDoctors());
        map.put("specialities",specialityService.findAll());
        map.put("specialityMessage", "Escolha uma especialidade");
        map.put(U,user);
        return "userListMedicos";
    }

    @PostMapping(value="/userListMedicosSpeciality")
    public ModelAndView userListMedicos(@RequestParam String specialityName) {
        ModelAndView mav = new ModelAndView("userListMedicos");
        mav.addObject("doctors",userService.findDoctorBySpeciality(specialityService.findByName(specialityName)));
        mav.addObject("specialities",specialityService.findAll());
        mav.addObject("specialityMessage", specialityName);
        mav.addObject("speciality", specialityName.toUpperCase());
        return mav;
    }

}

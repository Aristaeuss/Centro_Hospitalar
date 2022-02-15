package b3.CentroHospitalar.controllers;

import b3.CentroHospitalar.models.Invoice;
import b3.CentroHospitalar.models.ScheduledAppointment;
import b3.CentroHospitalar.models.Slot;
import b3.CentroHospitalar.models.Ticket;
import b3.CentroHospitalar.models.users.Doctor;
import b3.CentroHospitalar.models.users.Employee;
import b3.CentroHospitalar.models.users.Patient;
import b3.CentroHospitalar.models.users.User;
import b3.CentroHospitalar.repositories.DoctorRepository;
import b3.CentroHospitalar.models.users.*;
import b3.CentroHospitalar.services.*;
import b3.CentroHospitalar.services.Exceptions.*;
import b3.CentroHospitalar.services.InvoiceService;
import b3.CentroHospitalar.services.TicketService;
import b3.CentroHospitalar.services.UserImageService;
import b3.CentroHospitalar.services.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Controller
public class FuncionarioController {
    @Autowired
    UserService userService;
    @Autowired
    UserImageService userImageService;
    @Autowired
    TicketService ticketService;
    @Autowired
    InvoiceService invoiceService;
    @Autowired
    ScheduledAppointmentService scheduledAppointmentService;
    @Autowired
    SpecialityService specialityService;
    @Autowired
    SlotService slotService;
    @Autowired
    DoctorRepository dr;

    private static final String U="loggedInUser";

    @GetMapping(value = "/VistaGeralRecepcao")
    public String vgRecepcao(ModelMap map){
        map.put(U, userService.getLoggedUser());
        map.put("pendingInvoices", invoiceService.getAllPendingInvoices());
        map.put("tickets",ticketService.getTicketsToday(ticketService.getTicketsWaiting()));
        map.put("lateScheduledAppointments",scheduledAppointmentService.allLateNotCheckedInScheduledAppointments());
        return "VistaGeralRecepcao";
    }

    @GetMapping(value = "/listaFaturasFuncionario")
    public String listaFaturas(ModelMap map) {
        map.put(U, userService.getLoggedUser());
        map.put("invoices", invoiceService.getAllInvoices());
        return "listaFaturasFuncionario";
    }

    @GetMapping(value = "/listaFaturasFuncionario/{filter}")
    public String listaFaturasFiltradas(ModelMap map, @PathVariable String filter) {
        map.put(U, userService.getLoggedUser());
        List<Invoice> invoices;
        switch (filter) {
            case "todas":
                invoices = invoiceService.getAllInvoices(); break;
            case "pendentes":
                invoices = invoiceService.getAllPendingInvoices(); break;
            case "pagas":
                invoices = invoiceService.getAllPayedInvoices(); break;
            default:
                invoices = null; break;
        }
        map.put("invoices", invoices);
        return "listaFaturasFuncionario";
    }

    @GetMapping(value = "/listaFaturasFuncionario/{filter}/{userId}")
    public String listaFaturasFiltradasParaUtilizador(ModelMap map, @PathVariable String filter,
                                                                    @PathVariable String userId) {
        map.put(U, userService.getLoggedUser());
        int userIdInt = Integer.parseInt(userId);
        Patient user = userService.findPatientByNif(userIdInt);
        List<Invoice> invoices;
        if (user != null) {
            switch (filter) {
                case "todas":
                    invoices = invoiceService.getInvoicesFor(user); break;
                case "pendentes":
                    invoices = invoiceService.getPendingInvoicesFor(user); break;
                case "pagas":
                    invoices = invoiceService.getPaidInvoicesFor(user); break;
                default:
                    invoices = null; break;
            }
        } else {
            invoices = null;
        }
        map.put("invoices", invoices);
        map.put("userId", userId);
        return "listaFaturasFuncionario";
    }

    @PostMapping(value = "/listaFaturasFuncionario")
    public String listaFaturasNifFiltro(@RequestParam String nif) {
        return "redirect:/listaFaturasFuncionario/todas" + (nif.equals("") ? "" : "/") + nif;
    }

    @GetMapping(value = "/pagarFaturaFuncionario/{invoiceId}")
    public String faturaPaga(ModelMap map, @PathVariable(name="invoiceId") String invoiceId) {
        Invoice invoice = invoiceService.findById(invoiceId);
        if (invoice == null) {
            return "redirect:/landingPage";
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    invoiceService.pay(invoice);
                } catch (InvoiceAlreadyPayedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }).start();
        map.put(U,userService.getLoggedUser());
        map.put("invoice", invoice);
        return "faturaPaga";
    }

    @GetMapping(value="/listaUtentesEmEsperaFuncionario")
    public ModelAndView listaUtentesEmEsperaFuncionario(){
        ModelAndView mav=new ModelAndView("listaUtentesEmEsperaFuncionario");
        mav.addObject(U,userService.getLoggedUser());
        mav.addObject("tickets",ticketService.getTicketsToday(ticketService.getTicketsWaiting()));
        return mav;
    }

    @GetMapping(value = "/medicos")
    public String medicosFuncionario(ModelMap map){
        map.put(U,userService.getLoggedUser());
        map.put("doctorsToday",userService.findDoctorsScheduled());
        map.put("doctors",userService.getDoctors());
        return "medicos";
    }

    @GetMapping(value = "/listaMedicosPresentesFuncionario")
    public String listaMedicosPresentesFuncionario(ModelMap map){
        map.put(U,userService.getLoggedUser());
        map.put("doctors",userService.findDoctorsScheduled());
        map.put("specialities",specialityService.findAll());
        map.put("specialityMessage", "Escolha uma especialidade");
        map.put("doctorsToday",true);
        return "listaMedicosFuncionario";
    }



    @PostMapping(value = "/listaMedicosPresentesFuncionario")
    public ModelAndView listaMedicosFuncionario(@RequestParam String specialityName){
        ModelAndView mav = new ModelAndView("listaMedicosFuncionario");
        mav.addObject(U,userService.getLoggedUser());
        mav.addObject("doctors",userService.findDoctorsBySpeciality(userService.findDoctorsScheduled(),specialityService.findByName(specialityName)));
        //mav.addObject("doctors",userService.findDoctorBySpeciality(specialityService.findByName(specialityName)));
        mav.addObject("specialities", specialityService.findAll());
        mav.addObject("specialityMessage", specialityName);
        mav.addObject("doctorsToday",true);
        return mav;
    }


    @GetMapping(value = "/consultasFuncionario")
    public String consultas(ModelMap map) {
        User loggedUser = userService.getLoggedUser();
        map.put(U, loggedUser);
        return "consultasFuncionario";
    }

    @PostMapping(value = "/verConsultasFuncionario")
    public ModelAndView verConsultas(@RequestParam int nif) {
        ModelAndView mav= new ModelAndView();
        mav.addObject(U, userService.getLoggedUser());
        User patient = userService.findPatientByNif(nif);
        if (patient == null) {
            mav.addObject("errorAppointmentsList", "Não foi encontrado nenhum utilizador com esse NIF.");
            mav.setViewName("consultasFuncionario");
            return mav;
        }
        mav.setViewName("redirect:/verConsultasFuncionario/" + patient.getNif());
        return mav;
    }


    @GetMapping(value = "/verConsultasFuncionario/{nif}")
    public ModelAndView verConsultasNif(@PathVariable String nif) {
        ModelAndView mav= new ModelAndView();
        mav.addObject(U, userService.getLoggedUser());
        User patient = userService.findPatientByNif(Integer.parseInt(nif));
        if (patient == null) {
            mav.setViewName("redirect:/landingPage");
            return mav;
        }
        mav.addObject("scheduledAppointments", scheduledAppointmentService.allNotYetHappenedScheduledAppointmentsOf(patient));
        mav.addObject("patient", patient);
        mav.setViewName("listaConsultasFuncionario");
        return mav;
    }

    @GetMapping(value = "/cancelarConsultaFuncionario/{scheduledAppointmentId}")
    public ModelAndView cancelarConsultaFuncionario(@PathVariable String scheduledAppointmentId) {
        ModelAndView mav= new ModelAndView();
        mav.addObject(U, userService.getLoggedUser());
        ScheduledAppointment scheduledAppointment = scheduledAppointmentService.findById(Long.parseLong(scheduledAppointmentId));
        if (scheduledAppointment == null) {
            mav.setViewName("redirect:/landingPage");
            return mav;
        }
        mav.addObject("scheduledAppointment", scheduledAppointment);
        mav.setViewName("escolherCausaCancelamentoFuncionario");
        return mav;
    }

    @PostMapping(value = "/cancelarConsultaFuncionario/confirmado/{scheduledAppointmentId}")
    public ModelAndView cancelarConsultaFuncionarioConfirmado(@PathVariable String scheduledAppointmentId,
                                                              @RequestParam String cancelReason) {
        ModelAndView mav = new ModelAndView();
        mav.addObject(U, userService.getLoggedUser());
        ScheduledAppointment scheduledAppointment = scheduledAppointmentService.findById(Long.parseLong(scheduledAppointmentId));
        if (scheduledAppointment == null) {
            mav.setViewName("redirect:/landingPage");
            return mav;
        }
        try {
            if (cancelReason.equals("utente")) {
                scheduledAppointmentService.cancel(scheduledAppointment, false);
            } else {
                scheduledAppointmentService.cancel(scheduledAppointment, true);
            }
        } catch (ScheduledAppointmentAlreadyCanceledException e) {
            System.out.println(e.getMessage());
        }
        mav.addObject("scheduledAppointment", scheduledAppointment);
        mav.addObject("canceledByEmployee", true);
        mav.setViewName("consultaCancelada");
        return mav;
    }

    @GetMapping(value = "/listaMedicosFuncionario")
    public String listaMedicosFuncionario(ModelMap map){
        map.put(U,userService.getLoggedUser());
        map.put("doctors",userService.getDoctors());
        map.put("specialities",specialityService.findAll());
        map.put("specialityMessage", "Escolha uma especialidade");
        return "listaMedicosFuncionario";
    }

    @PostMapping(value = "/listaMedicosFuncionario")
    public ModelAndView listaMedicosFuncionarioPost(@RequestParam String specialityName){
        ModelAndView mav = new ModelAndView("listaMedicosFuncionario");
        mav.addObject(U,userService.getLoggedUser());
        mav.addObject("doctors",userService.findDoctorBySpeciality(specialityService.findByName(specialityName)));
        //mav.addObject("doctors",userService.findDoctorBySpeciality(specialityService.findByName(specialityName)));
        mav.addObject("specialities", specialityService.findAll());
        mav.addObject("specialityMessage", specialityName);
        return mav;
    }


    @GetMapping(value = "/ListaEspera")
    public String listaEspera(ModelMap map){
        map.put(U,userService.getLoggedUser());
        return "ListaEspera";
    }

    @GetMapping(value = "/utentesFuncionario")
    public String utentesFuncionario(ModelMap map){
        map.put(U,userService.getLoggedUser());
        map.put("tickets",ticketService.getTicketsToday(ticketService.getTicketsWaiting()));
        map.put("lateScheduledAppointments",scheduledAppointmentService.allLateNotCheckedInScheduledAppointments());
        return "utentesFuncionario";
    }

    @GetMapping(value="/listaUtentesAtrasadosFuncionario")
    public ModelAndView listaUtentesAtrasadosFuncionario(){
        ModelAndView mav=new ModelAndView("listaUtentesAtrasadosFuncionario");
        mav.addObject(U,userService.getLoggedUser());
        mav.addObject("lateScheduledAppointments",scheduledAppointmentService.allLateNotCheckedInScheduledAppointments());
        return mav;
    }

    @PostMapping(value="/pesquisaUtenteNIF")
    public ModelAndView findPatientByNif(@RequestParam int nif){
        ModelAndView mav=new ModelAndView("pesquisaUtentesFuncionario");
        Patient patient =userService.findPatientByNif(nif);
        mav.addObject(U,userService.getLoggedUser());
        if(patient==null){
            mav.addObject("errorMessageNIF","Não foi encontrado nenhum utente com esse NIF");
            mav.addObject("tickets",ticketService.getTicketsToday(ticketService.getTicketsWaiting()));
            mav.addObject("lateScheduledAppointments",scheduledAppointmentService.allLateNotCheckedInScheduledAppointments());
            mav.setViewName("utentesFuncionario");
            return mav;
        }
        mav.addObject("utente",patient);
        return mav;
    }

    @PostMapping(value="/pesquisaUtenteF")
    public ModelAndView findPatientByName(@RequestParam String name){
        ModelAndView mav=new ModelAndView("pesquisaUtentesFuncionario");
        mav.addObject(U, userService.getLoggedUser());
        List<Patient> patientsList=userService.findPatientByName(name);
        if(patientsList.isEmpty()){
            mav.addObject("errorMessageName","Não foi encontrado nenhum utente com esse nome");
            mav.addObject("tickets",ticketService.getTicketsToday(ticketService.getTicketsWaiting()));
            mav.addObject("lateScheduledAppointments",scheduledAppointmentService.allLateNotCheckedInScheduledAppointments());
            mav.setViewName("utentesFuncionario");
            return mav;
        }
        mav.addObject("utentes",patientsList);
        return mav;
    }

    @GetMapping(value = "/perfilFuncionario")
    public String perfilFuncionario(ModelMap map){
        map.put(U,userService.getLoggedUser());
        return "perfilFuncionario";
    }

    @PostMapping(value="/alterarMoradaF")
    public String alterarMoradaF(ModelMap map,
                                @RequestParam String address,
                                @RequestParam String door,
                                @RequestParam String floor,
                                @RequestParam String postalCode,
                                @RequestParam String city){
        User user= userService.getLoggedUser();
        userService.updateAddress(user, address, door, floor, postalCode, city);
        map.put(U, user);
        return "perfilFuncionario";
    }

    @PostMapping(value="/alterarTelefoneF")
    public String alterarTelefoneF(ModelMap map, @RequestParam String telephone){
        User user= userService.getLoggedUser();
        userService.updateTelephone(user,telephone);
        map.put(U,user);
        return "perfilFuncionario";
    }

    @PostMapping(value="/alterarImagemF")
    public String alterarImagemF(ModelMap map, @RequestParam MultipartFile file) {
        User user= userService.getLoggedUser();
        userImageService.updateImageFor(user,file);
        map.put(U,user);
        return"perfilFuncionario";
    }

    @PostMapping(value="/alterarNomeF")
    public String alterarPreferredNameF(ModelMap map, @RequestParam String name){
        User user=userService.getLoggedUser();
        userService.updatePreferredName(user,name);
        map.put(U,user);
        return "perfilFuncionario";
    }


    @GetMapping(value="/Registos")
    public String registos(ModelMap map){
        map.put(U,userService.getLoggedUser());
        return "RegistosFuncionario";
    }

    @GetMapping(value="/Registos/Utente")
    public String registosUtente(ModelMap map){
        map.put(U,userService.getLoggedUser());
        return "RegistosFuncionarioU";
    }



    @PostMapping(value = "/registoFuncionarioU")
    public ModelAndView register2 (@ModelAttribute Patient patient) {
        ModelAndView mav = new ModelAndView("/RegistosFuncionario");
        mav.addObject(U,userService.getLoggedUser());
        mav.addObject("success","registo efetuado com sucesso");
        userService.verifyPatientRegisterWithDefaultPassword(mav,patient);
        return mav;
    }

    @PostMapping(value = "/checkIn")
    public ModelAndView checkin(@RequestParam int nif) {
        ModelAndView mav= new ModelAndView();
        mav.addObject(U, userService.getLoggedUser());
        try {
            Ticket ticket=ticketService.createTicket(nif);
            if (ticket==null) {
                mav.addObject("errorCheckin","Não foram encontradas consultas marcadas para hoje");
                mav.addObject("pendingInvoices", invoiceService.getAllPendingInvoices());
                mav.addObject("tickets",ticketService.getTicketsToday(ticketService.getTicketsWaiting()));
                mav.addObject("lateScheduledAppointments",scheduledAppointmentService.allLateNotCheckedInScheduledAppointments());
                mav.setViewName("VistaGeralRecepcao");
                return mav;
            }
            mav.addObject("ticket",ticket);
        }catch (InexistentUserException e){
            mav.addObject("errorCheckin", "Não foi encontrado nenhum utilizador com esse NIF.");
            mav.addObject("pendingInvoices", invoiceService.getAllPendingInvoices());
            mav.addObject("tickets",ticketService.getTicketsToday(ticketService.getTicketsWaiting()));
            mav.addObject("lateScheduledAppointments",scheduledAppointmentService.allLateNotCheckedInScheduledAppointments());
            mav.setViewName("VistaGeralRecepcao");
            return mav;
        }
        mav.setViewName("confirmacaoSenha");
        return mav;
    }

    @GetMapping(value="perfilUtenteStatic/{id}")
    public ModelAndView verPerfilUtente(@PathVariable(name="id") String id){
        ModelAndView mav= new ModelAndView("perfilUtenteStatic");
        mav.addObject(U, userService.getLoggedUser());
        Patient patient= userService.findPatientByNif(Integer.parseInt(id));
        mav.addObject("patient",patient);
        return mav;
    }


    @GetMapping(value="perfilMedicoStatic/{id}")
    public ModelAndView verPerfilMedico(@PathVariable(name="id") String id){
        ModelAndView mav= new ModelAndView("perfilMedicoStatic");
        mav.addObject(U, userService.getLoggedUser());
        Doctor doctor= userService.findDoctorByNif(Integer.parseInt(id));
        mav.addObject("doctor",doctor);
        return mav;
    }

    @GetMapping(value="calendarioFuncionario")
    public ModelAndView calendarioFuncionario(){
        ModelAndView mav=slotService.daysBeforeFilter();
        mav.addObject(U,userService.getLoggedUser());
        mav.addObject("specialities",specialityService.findAll());
        mav.addObject("doctorMessage", "Antes de escolher o médico escolha a sua especialidade");
        mav.addObject("specialityMessage","Escolha uma especialidade");
        mav.addObject("doctors",null);
        mav.setViewName("calendarFuncionario");
        return mav;
    }

    @PostMapping(value="/calendarioFuncionarioE")
    public ModelAndView calendarioFuncionarioE(@RequestParam String specialityName){
        ModelAndView mav= slotService.getAvailabilitiesBySpeciality(specialityName);
        mav.addObject(U,userService.getLoggedUser());
        mav.addObject("doctorMessage", "Escolha um(a) médico(a)");
        mav.addObject("specialityMessage",specialityName);
        Speciality speciality=specialityService.findByName(specialityName);
        mav.addObject("doctors",userService.findDoctorBySpeciality(speciality));
        LocalDate today= LocalDate.now();
        List<Doctor> doctorsAvailableTodayBySpeciality=userService.findDoctorsBySpecialityByDay(userService.findDoctorBySpeciality(speciality),specialityName,today);
        mav.addObject("doctorsAvailableForDay",doctorsAvailableTodayBySpeciality);
        mav.setViewName("calendarFuncionario");
        List<Slot> slotsForFirstDoctor= slotService.slotsAvailableForFirstDoctorAndDateThatHaveNotHappenedYet(doctorsAvailableTodayBySpeciality,today);
        mav.addObject("slotsForDoctor",slotsForFirstDoctor);
        mav.addObject("dateSelected", LocalDateTime.now());
        return mav;
    }

    @PostMapping(value="/calendarioFuncionarioM")
    public ModelAndView calendarioFuncionarioM(@RequestParam String doctorName, String speciality){
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
            mav.setViewName("calendarFuncionarioSemPopUp");
            return mav;
        }
        int orderNumber=Integer.parseInt(doctorName);
        ModelAndView mav= slotService.getAvailabilitiesByDoctor(orderNumber);
        mav.addObject(U,userService.getLoggedUser());
        mav.addObject("doctorMessage", "Escolha um(a) médico(a)");

        List<Slot> slotsForDoctorForDate=slotService.getSlotsForDoctorAndDateThatHaveNotHappenedYet(userService.findDoctorByOrderNumber(orderNumber),LocalDate.now());
        mav.addObject("slotsForDoctor", slotsForDoctorForDate);
        mav.addObject("dateSelected", LocalDate.now());
        mav.setViewName("calendarFuncionarioSemPopUp");
        return mav;
    }

    @GetMapping(value="/calendarioFuncionario/{dayMonthYear}")
    public ModelAndView chooseDayFuncionario(@PathVariable(name="dayMonthYear") String dayMonthYearString){
        String[] split=dayMonthYearString.split(",");
        ModelAndView mav;
        if(split.length>4){
            mav=slotService.checkSlotsAvailableByDoctorAndDate(dayMonthYearString);
        }else {
            mav = slotService.checkDoctorsAvailableByDateAndSpeciality(dayMonthYearString);
        }
        mav.addObject(U,userService.getLoggedUser());
        mav.setViewName("calendarFuncionarioSemPopUp");
        return mav;
    }

    @GetMapping(value="/calendarioFuncionarioL/{dateDoctor}")
    public ModelAndView chooseDayDoctorListFuncionario(@PathVariable(name="dateDoctor") String dateDoctor){
        ModelAndView mav=slotService.checkSlotsAvailableByDoctorAndDate(dateDoctor);
        mav.addObject(U,userService.getLoggedUser());
        mav.setViewName("calendarFuncionarioSemPopUp");
        return mav;
    }

    @GetMapping (value = "/marcarConsultaFuncionario/{slotId}")
    public String marcarConsultaFuncionario(ModelMap map,@PathVariable(name="slotId") String slotID) {
        User user=userService.getLoggedUser();
        Long slotIDlong = Long.parseLong(slotID);
        Slot slot = slotService.findById(slotIDlong);
        map.put(U,user);
        map.put("slot",slot);
        return "marcarConsultaFuncionario";
    }

    @PostMapping (value = "/consultaMarcadaFuncionario/{slotId}")
    public String consultaMarcadaFuncionario(ModelMap map,@PathVariable(name="slotId") String slotID,
                                                          @RequestParam(name="nif") int nif) {
        map.put(U,userService.getLoggedUser());
        Long slotIDlong = Long.parseLong(slotID);
        User user = userService.findPatientByNif(nif);
        if (user == null) {
            map.put("errorMessage", "Não foi encontrado utilizador com esse NIF");
            Slot slot = slotService.findById(slotIDlong);
            map.put("slot", slot);
            return "marcarConsultaFuncionario";
        }
        ScheduledAppointment scheduledAppointment;
        try {
            scheduledAppointment=slotService.bookSlot(slotIDlong, user);
        } catch (IncorrectUserTypeException | NoSuchSlotException e){
            System.out.println(e.getMessage());
            return "redirect:/landingPage";
        } catch (SlotAlreadyBookedException e) {
            map.put("errorMessage", "A vaga selecionada ja foi occupada");
            return "consultaMarcadaFuncionario";
        }
        map.put("scheduledAppointment",scheduledAppointment);
        return "consultaMarcadaFuncionario";
    }

    @GetMapping(value = "/remarcarConsultaFuncionario/{scheduledAppointmentId}")
    public ModelAndView remarcarConsultaFuncionario(@PathVariable(name="scheduledAppointmentId") String scheduledAppointmentIdString) {
        User user=userService.getLoggedUser();
        Long scheduledAppointmentId = Long.parseLong(scheduledAppointmentIdString);
        ScheduledAppointment scheduledAppointment = scheduledAppointmentService.findById(scheduledAppointmentId);
        Doctor doctor = scheduledAppointment.getSlot().getDoctor();
        ModelAndView mav= slotService.getAvailabilitiesByDoctor(doctor.getOrderNumber());
        mav.addObject(U, user);
        Gson gson = new Gson();
        mav.addObject("jsonScheduledAppointmentToCancelId", gson.toJson(scheduledAppointmentId));
        mav.addObject("scheduledAppointmentToCancel", scheduledAppointment);
        mav.setViewName("calendarFuncionarioSemPopUp");
        return mav;
    }

    @GetMapping(value = "/remarcarConsultaFuncionarioChooseDay/{scheduledAppointmentId}/{dayMonthYear}")
    public ModelAndView remarcarConsultaFuncionarioChooseDay(@PathVariable(name="scheduledAppointmentId") String scheduledAppointmentIdString,
                                                  @PathVariable(name="dayMonthYear") String dayMonthYearString) {
        User user=userService.getLoggedUser();
        Long scheduledAppointmentId = Long.parseLong(scheduledAppointmentIdString);
        ScheduledAppointment scheduledAppointment = scheduledAppointmentService.findById(scheduledAppointmentId);
        ModelAndView mav=slotService.checkDoctorsAvailableByDateAndSpeciality(dayMonthYearString);
        mav.addObject(U, user);
        Gson gson = new Gson();
        mav.addObject("jsonScheduledAppointmentToCancelId", gson.toJson(scheduledAppointmentId));
        mav.addObject("scheduledAppointmentToCancel", scheduledAppointment);
        mav.setViewName("calendarFuncionarioSemPopUp");
        return mav;
    }

    @GetMapping(value = "/remarcarConsultaFuncionario/{scheduledAppointmentToCancelId}/{slotId}")
    public String remarcarConsultaFuncionario(ModelMap map, @PathVariable(name="scheduledAppointmentToCancelId") String scheduledAppointmentToCancelIdString,
                                    @PathVariable(name="slotId") String slotID) {
        map.put(U,userService.getLoggedUser());
        Long scheduledAppointmentId = Long.parseLong(scheduledAppointmentToCancelIdString);
        ScheduledAppointment scheduledAppointmentToCancel = scheduledAppointmentService.findById(scheduledAppointmentId);
        if (scheduledAppointmentToCancel == null) {
            return "redirect:/landingPage";
        }
        User user = scheduledAppointmentToCancel.getPatient();
        ScheduledAppointment newlyBookedScheduledAppointment;
        try {
            Long slot = Long.parseLong(slotID);
            newlyBookedScheduledAppointment=slotService.bookSlot(slot, user);
        }catch (IncorrectUserTypeException | NoSuchSlotException e){
            System.out.println(e.getMessage());
            return "redirect:/landingPage";
        } catch (SlotAlreadyBookedException e) {
            map.put("errorMessage", "A vaga selecionada ja foi occupada");
            return "consultaRemarcadaFuncionario";
        }
        map.put("newlyBookedScheduledAppointment", newlyBookedScheduledAppointment);
        try {
            scheduledAppointmentService.cancel(scheduledAppointmentToCancel, false);
        } catch (ScheduledAppointmentAlreadyCanceledException e) {
            System.out.println(e.getMessage());
        }
        map.put("canceledScheduledAppointment",scheduledAppointmentToCancel);
        return "consultaRemarcadaFuncionario";
    }

    @GetMapping(value = "/contactosFuncionario")
    public String contactosUtente(ModelMap map){
        map.put(U, userService.getLoggedUser());
        return "contactosFuncionario";
    }

    @GetMapping(value = "/infoFuncionario")
    public String infoUtente(ModelMap map){
        map.put(U, userService.getLoggedUser());
        return "infoFuncionario";
    }

}

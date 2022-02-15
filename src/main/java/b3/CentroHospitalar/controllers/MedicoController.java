package b3.CentroHospitalar.controllers;

import b3.CentroHospitalar.models.Appointment;
import b3.CentroHospitalar.models.Ticket;
import b3.CentroHospitalar.models.users.Doctor;
import b3.CentroHospitalar.models.users.Patient;
import b3.CentroHospitalar.models.users.User;
import b3.CentroHospitalar.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.print.Doc;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Optional;

@Controller
public class MedicoController {
    @Autowired
    UserService userService;
    @Autowired
    UserImageService userImageService;
    @Autowired
    TicketService ticketService;
    @Autowired
    ScheduledAppointmentService scheduledAppointmentService;
    @Autowired
    AppointmentService appointmentService;


    private static final String U="loggedInUser";


    ///página para ver o horário do médico logado
    @GetMapping(value = "/scheduleMedico")
    public String scheduleMedico(ModelMap map){
        map.put(U,userService.getLoggedUser());
        map.put("schedule", userService.getDoctorById(userService.getLoggedUser().getNif()).getSchedule());
        System.out.println(userService.getDoctorById(userService.getLoggedUser().getNif()).getSchedule());
        return "scheduleMedico";
    }

    ///página principal do médico após o login
    @GetMapping(value = "/VistaGeralMedico")
    public String vgMedico(ModelMap map){
        Doctor doctor=(Doctor)userService.getLoggedUser();
        map.put(U,doctor);
        if(doctor.getAppointmentTicketInProgress()!=null){
            map.put("doctorTicket",doctor.getAppointmentTicketInProgress());
            map.put("date", LocalDate.now());
            map.put("tickets",ticketService.getTicketsWaitingByDoctor((Doctor)userService.getLoggedUser()));
            map.put("appointments",scheduledAppointmentService.allScheduledAppointmentsForDoctorByDate((Doctor)userService.getLoggedUser(),LocalDate.now()));
            map.put("appointmentsLeft",scheduledAppointmentService.allScheduledAppointmentsForDoctorByDateNotYetAppointment((Doctor)userService.getLoggedUser(),LocalDate.now()));
            return "VistaGeralMedico";
        }
        Ticket nextTicket=ticketService.seeNextTicketByDoctor((Doctor)userService.getLoggedUser());
        fillAppointmentCards(map);
        return "VistaGeralMedico";
    }

    ///método auxiliar para preencher os cartões das páginas
    private void fillAppointmentCards(ModelMap map){
        Ticket nextTicket=ticketService.seeNextTicketByDoctor((Doctor)userService.getLoggedUser());
        map.put("nextTicket", nextTicket);
        map.put("isNextTicketLate",ticketService.didTicketMissTheAppointment(nextTicket));
        map.put("date", LocalDate.now());
        map.put("tickets",ticketService.getTicketsWaitingByDoctor((Doctor)userService.getLoggedUser()));
        map.put("appointments",scheduledAppointmentService.allScheduledAppointmentsForDoctorByDate((Doctor)userService.getLoggedUser(),LocalDate.now()));
        map.put("appointmentsLeft",scheduledAppointmentService.allScheduledAppointmentsForDoctorByDateNotYetAppointment((Doctor)userService.getLoggedUser(),LocalDate.now()));
    }

    ///página ao carregar em consultas na navBar
    @GetMapping(value = "/consultasMedico")
    public String consultasMedico(ModelMap map){
        Doctor doctor=(Doctor)userService.getLoggedUser();
        map.put(U,doctor);
        if(doctor.getAppointmentTicketInProgress()!=null){
            map.put("doctorTicket",doctor.getAppointmentTicketInProgress());
            map.put("date", LocalDate.now());
            map.put("tickets",ticketService.getTicketsWaitingByDoctor(doctor));
            map.put("appointments",scheduledAppointmentService.allScheduledAppointmentsForDoctorByDate(doctor,LocalDate.now()));
            map.put("appointmentsLeft",scheduledAppointmentService.allScheduledAppointmentsForDoctorByDateNotYetAppointment(doctor,LocalDate.now()));
            return "consultasMedico";
        }
        fillAppointmentCards(map);
        return "consultasMedico";
    }

    ///pagina com lista de utentes com checkin realizado
    @GetMapping(value = "/contactosMedico")
    public String contactosMedico(ModelMap map){
        map.put(U, userService.getLoggedUser());
        return "contactosMedico";
    }

    @GetMapping(value = "/infoMedico")
    public String infoMedico(ModelMap map){
        map.put(U, userService.getLoggedUser());
        return "infoMedico";
    }

    @GetMapping(value="/utentesEsperaMedico")
    public ModelAndView utentesEsperaMedico(){
        ModelAndView mav=new ModelAndView("listaUtentesEmEsperaMedico");
        mav.addObject(U,userService.getLoggedUser());
        mav.addObject("tickets",ticketService.getTicketsWaitingByDoctor((Doctor)userService.getLoggedUser()));
        return mav;
    }

    ///pagina com consultas do medico para o dia, realizadas ou não
    @GetMapping(value="/listaConsultasMedico")
    public ModelAndView listaConsultasMedico(){
        ModelAndView mav=new ModelAndView("listaConsultasMedico");
        mav.addObject(U,userService.getLoggedUser());
        mav.addObject("scheduledAppointments",scheduledAppointmentService.allScheduledAppointmentsForDoctorByDate((Doctor)userService.getLoggedUser(),LocalDate.now()));
        return mav;
    }

    ///ação chamar proxima senha
    @PostMapping(value = "/getNextTicket")
    public ModelAndView doctorNextTicket(){
        ModelAndView mav=new ModelAndView();
        Doctor doctor= (Doctor)userService.getLoggedUser();
        mav.addObject(U,doctor);
        mav.addObject("ticketsWaiting",ticketService.getTicketsWaitingByDoctor(doctor));
        mav.addObject("appointmentsToday",scheduledAppointmentService.allScheduledAppointmentsForDoctorByDateNotYetAppointment((Doctor)userService.getLoggedUser(), LocalDate.now()));
        ticketService.getNextTicketByDoctor(mav,doctor);
        mav.setViewName("MedicoConsulta");
        return mav;
    }

    ///ação fazer senha surgir novamente no ecrã
    @PostMapping(value="/callTicketAgain")
    public ModelAndView callTicketAgain(@RequestParam String ticket){
        Ticket realTicket = ticketService.findTicketById(ticket);
        ModelAndView mav=new ModelAndView();
        Doctor doctor= (Doctor)userService.getLoggedUser();
        mav.addObject(U,doctor);
        mav.addObject("ticketsWaiting",ticketService.getTicketsWaitingByDoctor(doctor));
        mav.addObject("appointmentsToday",scheduledAppointmentService.allScheduledAppointmentsForDoctorByDateNotYetAppointment(doctor, LocalDate.now()));
        ticketService.callTicketAgain(realTicket);
        mav.addObject("ticketCalledAgain",true);
        mav.addObject("ticket",realTicket);
        mav.setViewName("MedicoConsulta");
        mav.addObject("patientsAppointments",ticketService.getLastAppointmentsFromPatient(realTicket,3));
        return mav;
    }

    ///ação ao marcar falta do utente
    @PostMapping(value="/absentPatient")
    public ModelAndView absentPatient(@RequestParam String ticket){
        Ticket realTicket = ticketService.findTicketById(ticket);
        Doctor doctor=(Doctor)userService.getLoggedUser();
        ModelAndView mav=new ModelAndView();
        mav.addObject(U,doctor);
        mav.addObject("ticketsWaiting",ticketService.getTicketsWaitingByDoctor(doctor));
        mav.addObject("appointmentsToday",scheduledAppointmentService.allScheduledAppointmentsForDoctorByDateNotYetAppointment(doctor, LocalDate.now()));
        ticketService.patientAbsent(realTicket);
        mav.addObject("absentPatient",true);
        mav.setViewName("MedicoConsulta");
        return mav;
    }

    ///página para o médico chamar senhas e escrever as suas notas sobre ela
    @GetMapping(value = "/MedicoConsulta")
    public String MedicoConsulta(ModelMap map){
        map.put(U,userService.getLoggedUser());
        map.put("ticketsWaiting",ticketService.getTicketsWaitingByDoctor((Doctor)userService.getLoggedUser()));
        map.put("appointmentsToday",scheduledAppointmentService.allScheduledAppointmentsForDoctorByDateNotYetAppointment((Doctor)userService.getLoggedUser(), LocalDate.now()));
        if(((Doctor) userService.getLoggedUser()).getAppointmentTicketInProgress()!=null){
            Ticket ticket=((Doctor) userService.getLoggedUser()).getAppointmentTicketInProgress();
            map.put("ticket",ticket);
            map.put("patientsAppointments",ticketService.getLastAppointmentsFromPatient(ticket,3));
        }
        return "MedicoConsulta";
    }

    ///carregar página "MédicoConsulta" a partir da lista de utentes em espera
    @GetMapping(value="/chamarSenha/{id}")
    public String MedicoConsultaChamar(@PathVariable(name="id") String id, ModelMap map){
        map.put(U,userService.getLoggedUser());
        map.put("ticketsWaiting",ticketService.getTicketsWaitingByDoctor((Doctor)userService.getLoggedUser()));
        map.put("appointmentsToday",scheduledAppointmentService.allScheduledAppointmentsForDoctorByDateNotYetAppointment((Doctor)userService.getLoggedUser(), LocalDate.now()));
        Ticket ticket=ticketService.findTicketById(id);
        ticketService.callTicket((Doctor)userService.getLoggedUser(),map,ticket);
        return "MedicoConsulta";
    }

    ///ação para chamar senha que está mais do que 10minutos atrasada
    @PostMapping(value="/acceptTicket")
    public ModelAndView acceptLateTicket(@RequestParam String ticket){
        Ticket realTicket = ticketService.findTicketById(ticket);
        ticketService.markTicketAsCalled(realTicket);
        ModelAndView mav=new ModelAndView();
        mav.addObject(U,userService.getLoggedUser());
        mav.addObject("ticket",realTicket);
        mav.addObject("ticketsWaiting",ticketService.getTicketsWaitingByDoctor((Doctor)userService.getLoggedUser()));
        mav.addObject("appointmentsToday",scheduledAppointmentService.allScheduledAppointmentsForDoctorByDateNotYetAppointment((Doctor)userService.getLoggedUser(), LocalDate.now()));
        mav.setViewName("MedicoConsulta");
        mav.addObject("patientsAppointments",ticketService.getLastAppointmentsFromPatient(realTicket,3));
        return mav;
    }

    ///página para chamar outra senha e deixar utente atrasado ainda em espera
    @PostMapping(value="/waitTicket")
    public ModelAndView waitLateTicket(@RequestParam String ticket){
        Ticket realTicket = ticketService.findTicketById(ticket);
        ModelAndView mav= new ModelAndView();
        mav.addObject(U,userService.getLoggedUser());
        mav.addObject("ticketsWaiting",ticketService.getTicketsWaitingByDoctor((Doctor)userService.getLoggedUser()));
        ticketService.getNextTicketByDoctorThatIsNotThisOne(mav,(Doctor)userService.getLoggedUser(),realTicket);
        mav.addObject("appointmentsToday",scheduledAppointmentService.allScheduledAppointmentsForDoctorByDateNotYetAppointment((Doctor)userService.getLoggedUser(), LocalDate.now()));
        mav.setViewName("MedicoConsulta");
        return mav;
    }

    ///ação para recusar utente com um atraso >10minutos
    @PostMapping(value="/rejectTicket")
    public ModelAndView rejectLateTicket(@RequestParam String ticket){
        Ticket realTicket = ticketService.findTicketById(ticket);
        ticketService.rejectLateTicket(realTicket);
        ModelAndView mav=new ModelAndView();
        mav.addObject(U, userService.getLoggedUser());
        mav.addObject("lateTicketRejected",true);
        mav.addObject("ticketsWaiting",ticketService.getTicketsWaitingByDoctor((Doctor)userService.getLoggedUser()));
        mav.addObject("appointmentsToday",scheduledAppointmentService.allScheduledAppointmentsForDoctorByDateNotYetAppointment((Doctor)userService.getLoggedUser(), LocalDate.now()));
        mav.setViewName("MedicoConsulta");
        return mav;
    }

    ///ação gravar a consulta
    @PostMapping(value="/saveAppointment")
    public ModelAndView saveAppointment(@ModelAttribute Appointment appointment, @RequestParam String ticket){
        ModelAndView mav=new ModelAndView();
        Ticket realTicket = ticketService.findTicketById(ticket);
        appointmentService.officializeAppointment(appointment,realTicket.getScheduledAppointment());
        mav.addObject("appointmentSaved",true);
        mav.setViewName("MedicoConsulta");
        mav.addObject(U, userService.getLoggedUser());
        mav.addObject("ticketsWaiting",ticketService.getTicketsWaitingByDoctor((Doctor)userService.getLoggedUser()));
        mav.addObject("appointmentsToday",scheduledAppointmentService.allScheduledAppointmentsForDoctorByDateNotYetAppointment((Doctor)userService.getLoggedUser(), LocalDate.now()));
        return mav;
    }

    ///ver perfil do medico e editá-lo
    @GetMapping(value = "/perfilMedico")
    public String perfilMedico(ModelMap map){
        map.put(U,userService.getLoggedUser());
        return "perfilMedico";
    }

    ///alterar morada
    @PostMapping(value="/alterarMoradaM")
    public String alterarMorada(ModelMap map,
                                @RequestParam String address,
                                @RequestParam String door,
                                @RequestParam String floor,
                                @RequestParam String postalCode,
                                @RequestParam String city){
        User user= userService.getLoggedUser();
        userService.updateAddress(user, address, door, floor, postalCode, city);
        map.put(U, user);
        return "perfilMedico";
    }

    ///alterar numero de telefone
    @PostMapping(value="/alterarTelefoneM")
    public String alterarTelefone(ModelMap map, @RequestParam String telephone){
        User user= userService.getLoggedUser();
        userService.updateTelephone(user,telephone);
        map.put(U,user);
        return "perfilMedico";
    }

    ///alterar imagem de perfil
    @PostMapping(value="/alterarImagemM")
    public String alterarImagem(ModelMap map, @RequestParam MultipartFile file){
        User user= userService.getLoggedUser();
        userImageService.updateImageFor(user,file);
        map.put(U,user);
        return"perfilMedico";
    }

    ///alterar nome
    @PostMapping(value="/alterarNomeM")
    public String alterarPreferredName(ModelMap map, @RequestParam String name){
        User user=userService.getLoggedUser();
        userService.updatePreferredName(user,name);
        map.put(U,user);
        return "perfilMedico";
    }

    ///alterar aboutMe
    @PostMapping(value="/alterarSobre")
    public String alterarSobre(ModelMap map, @RequestParam String aboutMe){
        User user=userService.getLoggedUser();
        userService.updateAboutMe((Doctor)user,aboutMe);
        map.put(U,user);
        return "perfilMedico";
    }

    ///TODO remover?
    @GetMapping(value = "/horariosMedico")
    public String horariosMedico(ModelMap map){
        map.put(U, userService.getLoggedUser());
        return "horariosMedico";
    }

    ///pagina ao clicar em utentes na navBar
    @GetMapping(value = "/utentesMedico")
    public String utentesMedico(ModelMap map){
        map.put(U, userService.getLoggedUser());
        map.put("appointments",scheduledAppointmentService.allScheduledAppointmentsForDoctorByDate((Doctor)userService.getLoggedUser(),LocalDate.now()) );
        return "utentesMedico";
    }

    ///pesquisar utentes por nome
    @PostMapping(value="/pesquisaUtenteM")
    public ModelAndView pesquisaUtenteM(@RequestParam String name){
        ModelAndView mav=new ModelAndView("listaUtentesMedico");
        mav.addObject(U, userService.getLoggedUser());
        List<Patient> patientsList=userService.findPatientByName(name);
        if(patientsList.isEmpty()){
            mav.addObject("errorMessage","Não foi encontrado nenhum utente com esse nome");
            mav.setViewName("utentesMedico");
            return mav;
        }
        mav.addObject("utentes",patientsList);
        return mav;
    }

    ///ver utentes com consulta hoje
    @GetMapping (value="/listaUtentesMedicoHoje")
    public ModelAndView listaUtentesMedicoHoje(){
        ModelAndView mav=new ModelAndView("listaUtentesMedicoHoje");
        mav.addObject(U, userService.getLoggedUser());
        mav.addObject("utentes",scheduledAppointmentService.allScheduledAppointmentsForDoctorByDate((Doctor)userService.getLoggedUser(),LocalDate.now()));
        return mav;
    }

    ///ver historico de consultas do utente
    @GetMapping(value="/historicoUtente/{id}")
    public ModelAndView listPatientPreviousAppointments(@PathVariable(name="id") String id){
        ModelAndView mav=new ModelAndView("listaHistoricoConsultasMedico");
        mav.addObject(U, userService.getLoggedUser());
        Patient patient=userService.findPatientByNif(Integer.parseInt(id));
        mav.addObject("patient",patient);
        mav.addObject("appointments",appointmentService.findAllByPatient(patient));
        return mav;
    }

    @GetMapping(value="/detalhesConsultaMedico/{id}")
    public ModelAndView detalhesConsulta(@PathVariable(name="id") String id){
        ModelAndView mav=new ModelAndView("detalhesConsultaMedico");
        Appointment appointment=appointmentService.findById(Long.parseLong(id)).orElse(null);
        mav.addObject(U, userService.getLoggedUser());
        mav.addObject("appointment",appointment);
        return mav;
    }

}

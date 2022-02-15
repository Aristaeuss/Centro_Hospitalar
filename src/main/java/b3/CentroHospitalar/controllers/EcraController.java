package b3.CentroHospitalar.controllers;

import b3.CentroHospitalar.models.Ticket;
import b3.CentroHospitalar.services.Exceptions.InexistentUserException;
import b3.CentroHospitalar.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class EcraController {

    @Autowired
    TicketService ticketService;

    @GetMapping("/EcraSenhasChamadas")
    public ModelAndView verSenhas(){
        ModelAndView mav=new ModelAndView();
        mav.setViewName("EcraSenhasChamadas");
        mav.addObject("dateTime", LocalDateTime.now());
        mav.addObject("time",LocalDateTime.now().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        mav.addObject("senhas",ticketService.getLastEightCalledTickets());
        return mav;
    }

    @GetMapping("/SelfCheckIn")
    public ModelAndView selfCheckIn(){
        ModelAndView mav=new ModelAndView("EcraCheckIn");
        mav.addObject("dateTime", LocalDateTime.now());
        return mav;
    }

    @PostMapping(value = "/checkInMaquina")
    public ModelAndView checkin(@RequestParam int nif) {
        ModelAndView mav= new ModelAndView();
        try {
            Ticket ticket=ticketService.createTicket(nif);
            if (ticket==null) {
                mav.addObject("errorCheckin","Não foram encontradas consultas marcadas para hoje");
                mav.setViewName("EcraCheckIn");
                return mav;
            }
            mav.addObject("ticket",ticket);
        }catch (InexistentUserException e){
            mav.addObject("errorCheckin", "Não foi encontrado nenhum utilizador com esse NIF.");
            mav.setViewName("EcraCheckIn");
            return mav;
        }
        mav.setViewName("confirmacaoSenhaEcra");
        mav.addObject("dateTime",LocalDateTime.now());
        return mav;
    }

}

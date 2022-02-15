package b3.CentroHospitalar.controllers;

import b3.CentroHospitalar.models.Ticket;
import b3.CentroHospitalar.models.users.Admin;
import b3.CentroHospitalar.models.users.Doctor;
import b3.CentroHospitalar.repositories.TicketRepository;
import b3.CentroHospitalar.services.InvoiceService;
import b3.CentroHospitalar.services.SlotService;
import b3.CentroHospitalar.services.SpecialityService;
import b3.CentroHospitalar.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;

@Controller
public class LandingPageController {
    @Autowired
    SlotService slotService;
    @Autowired
    UserService userService;
    @Autowired
    InvoiceService invoiceService;
    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    SpecialityService specialityService;

    @GetMapping(value = {"/","/landingPage"})
    public String landingPage(){
        if(userService.findAllAdmins().isEmpty()){
            userService.firstStartUp();
        }
        return "landingPage";
    }

    @GetMapping(value = {"/listMedicos"})
    public String listMedicos(ModelMap map) {
        map.put("doctors", userService.getDoctors());
        map.put("specialities",specialityService.findAll());
        map.put("specialityMessage", "Escolha uma especialidade");
        return "listMedicos";
    }

    @PostMapping(value="/listMedicosSpeciality")
    public ModelAndView listMedicos(@RequestParam String specialityName) {
        ModelAndView mav = new ModelAndView("listMedicos");
        mav.addObject("doctors",userService.findDoctorBySpeciality(specialityService.findByName(specialityName)));
        mav.addObject("specialities",specialityService.findAll());
        mav.addObject("specialityMessage", specialityName);
        mav.addObject("speciality", specialityName.toUpperCase());
        return mav;
    }
}

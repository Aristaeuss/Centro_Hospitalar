package b3.CentroHospitalar.controllers;

import b3.CentroHospitalar.models.users.Doctor;
import b3.CentroHospitalar.models.users.Employee;
import b3.CentroHospitalar.models.users.Patient;
import b3.CentroHospitalar.models.users.Schedule;
import b3.CentroHospitalar.services.ScheduleService;
import b3.CentroHospitalar.services.SpecialityService;
import b3.CentroHospitalar.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {
    @Autowired
    UserService us;
    @Autowired
    ScheduleService scheduleService;
    @Autowired
    SpecialityService specialityService;


    private static final String U="loggedInUser";

    @GetMapping(value = "/VistaGeralAdmin")
    public String vgRecepcao(ModelMap map){
        map.put(U, us.getLoggedUser());
        return "VistaGeralAdmin";
    }

    @GetMapping(value="/gespecialidade")
    public String gEspecialidade(ModelMap map){
        map.put("loggedInUser",us.getLoggedUser());
        map.put("specialities",specialityService.findAll());
        return "GestaoEspecialidades";
    }

    @PostMapping(value="/apagarEspecialidade")
    public String apagarEspecialidade(ModelMap map,@RequestParam String specialityId){
        map.put("loggedInUser",us.getLoggedUser());
        if(!specialityService.deleteSpecialityById(specialityId)){
            map.put("failDeleteMessage","Por favor apague os médicos associados à especialidade antes de apagar a especialidade");
            map.put("specialities",specialityService.findAll());
            return "GestaoEspecialidades";
        }
        map.put("specialities",specialityService.findAll());
        map.put("successDeleteMessage","Especialidade apagada com sucesso");
        return "GestaoEspecialidades";
    }

    @PostMapping(value="/criarEspecialidade")
    public String criarEspecialidade(ModelMap map,@RequestParam String specialityName){
        map.put("loggedInUser",us.getLoggedUser());
        specialityService.newSpeciality(specialityName);
        map.put("specialities",specialityService.findAll());
        map.put("successCreateMessage","Especialidade criada com sucesso");
        return "GestaoEspecialidades";
    }

    @GetMapping(value = "/ghorarios")
    public String gHorario(ModelMap map){
        map.put("loggedInUser",us.getLoggedUser());
        map.put("doctors",us.getDoctors());
        return "GestaoHorarios";
    }


    @GetMapping(value = "/GestaoHorarios")
    public String gestaoHorarios(ModelMap map){
        map.put(U,us.getLoggedUser());
        return "GestaoHorarios";
    }

    @PostMapping(value = "/gestaoHorarios")
    public String gerirHorarios (ModelMap map,@ModelAttribute Schedule schedule, @RequestParam String doctorOrderNumber) {
        int drOrderNumber = Integer.parseInt(doctorOrderNumber);
        System.out.println(drOrderNumber);
        Doctor dr= us.findDoctorByOrderNumber(drOrderNumber);
        scheduleService.setDoctor(dr,schedule);
        System.out.println(dr);
        scheduleService.saveSchedule(schedule);
        System.out.println(schedule);
        us.setSchedule(dr, schedule);
        us.updateUser(dr);
        System.out.println("schedule saved");
        map.put("doctors",us.getDoctors());
        map.put("loggedInUser",us.getLoggedUser());
        map.put("success",true);
        return "GestaoHorarios";
    }

    @GetMapping(value="/RegistosA")
    public String registos(ModelMap map){
        map.put(U,us.getLoggedUser());
        return "RegistosAdmin";
    }


    @GetMapping(value="/RegistosA/Utente")
    public String registosUtente(ModelMap map){
        map.put(U,us.getLoggedUser());
        return "RegistosFuncionarioA";
    }

    @GetMapping(value="/Registos/Medico")
    public String registosMedico(ModelMap map){
        map.put(U,us.getLoggedUser());
        map.put("specialities",specialityService.findAll());
        return "RegistosFuncionarioM";
    }

    @GetMapping(value="/Registos/Funcionario")
    public String registosFuncionario(ModelMap map){
        map.put(U,us.getLoggedUser());
        return "RegistosFuncionarioF";
    }

    @PostMapping(value = "/registoFuncionarioM")
    public ModelAndView fRegisterDoctor (@ModelAttribute Doctor doctor, @RequestParam String specialityName) {
        ModelAndView mav = new ModelAndView("/RegistosAdmin");
        mav.addObject(U,us.getLoggedUser());
        us.verifyDoctorRegisterWithDefaultPassword(doctor, specialityName,mav);
        mav.addObject("success","registo efetuado com sucesso");
        return mav;
    }

    @PostMapping(value = "/registoFuncionarioF")
    public ModelAndView fRegisterEmployee (@ModelAttribute Employee employee) {
        ModelAndView mav = new ModelAndView("/RegistosAdmin");
        mav.addObject(U,us.getLoggedUser());
        mav.addObject("success","registo efetuado com sucesso");
        us.verifyEmployeeRegisterWithDefaultPassword(mav,employee);
        return mav;
    }

    @PostMapping(value = "/registoFuncionarioA")
    public ModelAndView register2 (@ModelAttribute Patient patient) {
        ModelAndView mav = new ModelAndView("/RegistosFuncionario");
        mav.addObject(U,us.getLoggedUser());
        mav.addObject("success","registo efetuado com sucesso");
        us.verifyPatientRegisterWithDefaultPasswordAdmin(mav,patient);
        return mav;
    }

}

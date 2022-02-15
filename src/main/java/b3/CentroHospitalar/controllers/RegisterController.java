package b3.CentroHospitalar.controllers;

import b3.CentroHospitalar.models.users.Doctor;
import b3.CentroHospitalar.models.users.Employee;
import b3.CentroHospitalar.models.users.Patient;
import b3.CentroHospitalar.models.users.Schedule;
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
public class RegisterController {
    @Autowired
    UserService us;
    @Autowired
    SpecialityService sp;

    @GetMapping(value = "/registo")
    public String registo(){
        return "registo";
    }


    @PostMapping(value = "/registo")
    public ModelAndView register2 (@ModelAttribute Patient patient) {
        ModelAndView mav=us.verifyPatientRegister(patient);
        return mav;
    }


    @GetMapping(value = "/registoM")
    public String registoM(ModelMap map){
        map.put("specialities",sp.findAll());
        return "registoMedico";
    }


    @PostMapping(value = "/registoM")
    public ModelAndView registerDoctor (@ModelAttribute Doctor doctor, @RequestParam String specialityName) {
        System.out.println("asdf");
        ModelAndView mav= new ModelAndView("/login");
        us.verifyDoctorRegister(doctor,specialityName,mav);
        return mav;
    }

    @GetMapping(value = "/registoF")
    public String registoF(){
        return "registoFuncionario";
    }


    @PostMapping(value = "/registoF")
    public ModelAndView registerEmployee (@ModelAttribute Employee employee) {
        System.out.println("asdf");
        ModelAndView mav = new ModelAndView("/login");
        us.verifyEmployeeRegister(mav,employee);
        return mav;
    }



}

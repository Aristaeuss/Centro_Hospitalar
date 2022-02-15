package b3.CentroHospitalar.controllers;

import b3.CentroHospitalar.models.GenericResponse;
import b3.CentroHospitalar.models.PasswordResetToken;
import b3.CentroHospitalar.models.users.User;
import b3.CentroHospitalar.repositories.DoctorRepository;
import b3.CentroHospitalar.repositories.PasswordResetTokenRepository;
import b3.CentroHospitalar.repositories.UserRepository;
import b3.CentroHospitalar.services.UserSecurityService;
import b3.CentroHospitalar.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private static final java.util.UUID UUID = null;

    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    UserSecurityService securityService;
    @Autowired
    UserService us;
    @Autowired
    PasswordResetTokenRepository prtr;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping(value="/login-error")
    public String loginError(ModelMap map){
        map.put("errorMessage","O username ou password estão incorrectos");
        return "login";
    }

    @GetMapping(value = "/login")
    public String login(ModelMap map,@RequestParam(value = "error", defaultValue = "false") boolean loginError){
        if (loginError) {
            map.put("errorMessage","O username ou password estão incorrectos");
        }
        return "login";
    }

    @GetMapping(value = "/recuperarAcesso")
    public String recuperarAcesso(){
        return "recuperarAcesso";
    }

    @PostMapping("/recuperarAcesso")
    public ModelAndView recuperarAcesso(HttpServletRequest request, @RequestParam("email") String userEmail) {
        ModelAndView mav = new ModelAndView();
        User user = us.findUserByEmail(userEmail);
        if (user == null) {
            mav.addObject("errorMessageEmail", "O email indicado não consta da nossa base de dados");
            mav.setViewName("/recuperarAcesso");
        } else {
            String token = UUID.randomUUID().toString();
            us.createPasswordResetTokenForUser(user, token);
            mailSender.send(constructResetTokenEmail("localhost:8080", token, user));
            new GenericResponse("message.recuperarAcessoEmail");
            mav.addObject("successMessageEmail", "Um link de recuperação de acesso foi enviado para o seu email (pode fechar esta janela)");
            mav.setViewName("/recuperarAcesso");
        }
        return mav;
    }

    @GetMapping("/alterarPassword/{token}")
    public String showChangePasswordPage(@PathVariable(name="token") String token, Model model) {
        String result = securityService.validatePasswordResetToken(token);
        if(result != null) {
            return "/login";
        } else {
            securityService.useToken(token);
            System.out.println("chegar");
            model.addAttribute("token", token);
            return "/alterarPassword";
        }
    }

    @PostMapping("/alterarPasswordp")
    public String saveNewPassword(HttpServletRequest request, @RequestParam String password, String matchPassword, String token, ModelMap model) {
        if(!password.equals(matchPassword)){
            model.put("token", token);
            model.put("errorMessagePassword", "As passwords não são iguais");
            System.out.println("alterar");
            return "redirect:/alterarPassword/" + token;
        }
        System.out.println("pw iguais");
        User user = prtr.findByToken(token).getUser();
            us.changePassword(user, password);
            us.saveUser(user);
            return "redirect:/login";
    }

    private SimpleMailMessage constructResetTokenEmail(String contextPath, String token, User user) {
        String url = contextPath + "/alterarPassword/" + token;
        return constructEmail("Recuperação de Acesso","Olá\nPara recuperar o seu acesso clique no link abaixo:\r\n" + url, user);
    }

    private SimpleMailMessage constructEmail(String subject, String body,
                                             User user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        return email;
    }

}

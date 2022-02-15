package b3.CentroHospitalar.config;

import b3.CentroHospitalar.models.users.*;
import b3.CentroHospitalar.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticatorProvider implements AuthenticationProvider {

    @Autowired
    UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userService.authenticate(name, password);

        System.out.println("O user logado é: "+user);
        if (user!=null) {

            List<GrantedAuthority> roleList = new ArrayList<>();

            if(userService.findPatientByUsername(user.getUsername())!=null){
                Patient patient=userService.findPatientByUsername(user.getUsername());
                roleList = patient.getAuthorities();
            }
            if(userService.findDoctorByUsername(user.getUsername())!=null){
                Doctor doctor = userService.findDoctorByUsername(user.getUsername());
                roleList = doctor.getAuthorities();
            }
            if(userService.findEmployeeByUsername(user.getUsername())!=null){
                Employee employee= userService.findEmployeeByUsername(user.getUsername());
                roleList = employee.getAuthorities();
            }
            if(userService.findAdminByUsername(user.getUsername())!=null){
                Admin admin= userService.findAdminByUsername(user.getUsername());
                roleList = admin.getAuthorities();
            }


            // use the credentials
            // and authenticate against the third-party system
            return new UsernamePasswordAuthenticationToken(
                    name, password, roleList);
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

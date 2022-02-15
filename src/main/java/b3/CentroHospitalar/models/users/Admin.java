package b3.CentroHospitalar.models.users;


import b3.CentroHospitalar.models.ScheduledAppointment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Admin extends User{

    public Admin(){}
    public Admin(String username, String password, String name, String preferedName, int nif, Date dob, int telephone, String email) {
        super(username,password,name,preferedName,nif,dob,telephone,email);
    }

    @Override
    public List<GrantedAuthority> getAuthorities(){
        List<GrantedAuthority> roles=new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(Roles.ROLE_ADMIN.name()));
        return roles;
    }

    @Override
    public String toString(){
        return super.getName()+getEmail()+getUsername()+getDob()+getNif();
    }
}

package b3.CentroHospitalar.models.users;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Employee extends User{


    private int iban;

    public Employee (){}

    public Employee(String username, String password, String name, String preferredName, int nif, Date dob, int telephone, String email, int iban) {
        super(username, password, name, preferredName, nif, dob, telephone, email);
        this.iban = iban;
    }

    @Override
    public List<GrantedAuthority> getAuthorities(){
        List<GrantedAuthority> roles=new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(Roles.ROLE_EMPLOYEE.name()));
        return roles;
    }

    public int getIban() {
        return iban;
    }

    public void setIban(int iban) {
        this.iban = iban;
    }

    @Override
    public UserImage getUserImage() {
        if(image==null){
            return new UserImage(this,"../Images/perfilFuncionario.svg");
        }
        return image;
    }

}

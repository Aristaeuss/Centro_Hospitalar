package b3.CentroHospitalar.models.users;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Speciality {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany
    private Collection<Doctor> doctors;

    public Speciality(){}

    public Speciality(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

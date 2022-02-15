package b3.CentroHospitalar.models.users;

import b3.CentroHospitalar.models.users.User;

import javax.persistence.*;

@Entity
@Table(name = "userimages")
public class UserImage {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User owner;

    private String path;

    public UserImage() {}

    public UserImage(User owner, String path) {
        this.owner = owner;
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getPath() {
        System.out.println(path);
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}

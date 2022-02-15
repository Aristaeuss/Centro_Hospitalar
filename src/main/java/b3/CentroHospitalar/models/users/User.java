package b3.CentroHospitalar.models.users;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "users")
@Component
public class User implements UserDetails {

    @Id
    @Column(name = "user_id")
    private int nif;
    private String username;
    private String password;
    @Transient
    private String confirmPassword;
    private String name;
    private String preferredName;
    private Date dob;
    private Date dateOfBirth;
    private int telephone;
    private String address;
    private String postalCode;
    private String doorNumber;
    private String floor;
    private String city;
    private String email;


    @Override
    public List<GrantedAuthority> getAuthorities(){
        List<GrantedAuthority> roles=new ArrayList<>();
        return roles;
    }

    private Boolean active;


    @OneToOne
    @JoinColumn(name = "image_id")
    protected UserImage image;


    public User() {
    }

    public User(int id, String email, String password, Boolean active) {
        this.email = email;
        this.password = password;
        this.active = active;
    }

    public User(String username, String password, String name, String preferredName, int nif, Date dob, int telephone, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.preferredName = preferredName;
        this.nif = nif;
        this.dob = dob;
        this.telephone = telephone;
        this.email = email;
    }

    public UserImage getUserImage() {
        if(image==null){
            return new UserImage(this,"../Images/perfilUtente.svg");
        }
        return image;
    }

    public void setUserImage(UserImage image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public long getAge(){
        LocalDate today = LocalDate.now();
        LocalDate birthday= dob.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        long age = ChronoUnit.YEARS.between(birthday, today);
        return age;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(String dob) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parsed = format.parse(dob);
        this.dob = parsed;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getDoorNumber() {
        return doorNumber;
    }

    public void setDoorNumber(String doorNumber) {
        this.doorNumber = doorNumber;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompleteAddress(){
        return getAddress()+" "+getDoorNumber()+" "+getFloor()+" - "+getPostalCode()+" "+getCity();
    }



    @Override
    public String toString() {
        return getName() + " " + getPreferredName() + " " + getUsername() + " " + getDob();
    }

}

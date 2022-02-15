package b3.CentroHospitalar.models;

import b3.CentroHospitalar.models.users.Patient;
import b3.CentroHospitalar.services.InvoiceServiceImpl;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Invoice {

    public static final double DEFAULT_APPOINTMENT_PRICE = 50.0;

    @Id
    private String id;
    private String name;
    private String email;
    private String nif;
    private String dueDate;
    private String issuedDate;
    private String paidDate;
    private String value;
    private String url;

    @OneToOne
    Appointment appointment;

    public Appointment getAppointment() {
        return appointment;
    }

    public LocalDate getLocalDatePartOfIssuedDate() {
        return dateTimeStringToLocalDate(issuedDate);
    }

    public LocalDate getLocalDatePartOfDueDate() {
        return dateTimeStringToLocalDate(dueDate);
    }

    public LocalDate getLocalDatePartOfPaidDate() {
        return dateTimeStringToLocalDate(paidDate);
    }

    private static LocalDate dateTimeStringToLocalDate(String dateTimeString) {
        if (dateTimeString == null) {
            return null;
        }
        String[] parts = dateTimeString.substring(0, 10).split("-");
        return LocalDate.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
    }

    public String getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(String paidDate) {
        this.paidDate = paidDate;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPaymentState(){
        if(getPaidDate()==null){
            return "Pendente";
        }else{
            return "Pago";
        }
    }
}

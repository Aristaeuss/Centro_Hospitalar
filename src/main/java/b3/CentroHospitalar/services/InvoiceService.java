package b3.CentroHospitalar.services;

import b3.CentroHospitalar.models.Invoice;
import b3.CentroHospitalar.models.users.Patient;
import b3.CentroHospitalar.models.users.User;
import b3.CentroHospitalar.services.Exceptions.InvoiceAlreadyPayedException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface InvoiceService {

    Invoice createInvoice(User user, double value);

    List<Invoice> getInvoicesFor(Patient p);

    List<Invoice> getPendingInvoicesFor(Patient p);

    Invoice getMostUrgentInvoiceFor(Patient p);

    List<Invoice> getPaidInvoicesFor(Patient p);

    List<Invoice> getAllInvoices();

    List<Invoice> getAllPendingInvoices();

    List<Invoice> getAllPayedInvoices();

    Invoice findById(String invoiceId);

    void pay(Invoice invoice) throws InvoiceAlreadyPayedException;
}

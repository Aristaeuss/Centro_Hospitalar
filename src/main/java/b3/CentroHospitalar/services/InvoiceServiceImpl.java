package b3.CentroHospitalar.services;

import b3.CentroHospitalar.models.Invoice;
import b3.CentroHospitalar.models.InvoiceResponse;
import b3.CentroHospitalar.models.users.Patient;
import b3.CentroHospitalar.models.users.User;
import b3.CentroHospitalar.repositories.InvoiceRepository;
import b3.CentroHospitalar.services.Exceptions.InvoiceAlreadyPayedException;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private static final String URL_BASE = "https://serro.pt/invoices/";
    private static final int COMPANY_NIF = 923184885;
    private static final String URL_REQUEST = URL_BASE + COMPANY_NIF + "/create";
    private static final String URL_PAY = URL_BASE + COMPANY_NIF + "/pay";

    public static final Duration TIME_TO_PAY_INVOICE = Duration.ofDays(7);
    private static final Duration TIME_TO_REPEAT_INVOICE_REQUEST = Duration.ofMinutes(1);

    public static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
    private static RestTemplate restTemplate = new RestTemplate();
    private static HttpHeaders headers = new HttpHeaders();
    static {
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Autowired
    InvoiceRepository invoiceRepository;

    private final static ReentrantLock lock = new ReentrantLock();
    /**
     * Thread may block on this method if Invoice service is not able to return Invoice immediately.
     */
    @Override
    public Invoice createInvoice(User user, double value) {
        JSONObject invoiceRequest = new JSONObject();
        invoiceRequest.put("name", user.getName());
        invoiceRequest.put("email", user.getEmail());
        invoiceRequest.put("nif", user.getNif());
        invoiceRequest.put("dueDate", df.format(Date.valueOf(LocalDate.now().plusDays(TIME_TO_PAY_INVOICE.toDays()))));
        invoiceRequest.put("value", "" + value);

        InvoiceResponse response;
        lock.lock();
        while (true) {
            long initialMillis = System.currentTimeMillis();
            response = restTemplate.postForObject(
                    URL_REQUEST, new HttpEntity<>(invoiceRequest.toString(), headers), InvoiceResponse.class);
            long millisPassed = System.currentTimeMillis() - initialMillis;
            if (response != null && !response.status.equals("error")) {
                break;
            }
            try {
                Thread.sleep(TIME_TO_REPEAT_INVOICE_REQUEST.toMillis() - millisPassed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lock.unlock();
        Invoice invoice = response.getInvoice();
        invoiceRepository.save(invoice);
        return invoice;
    }

    @Override
    public List<Invoice> getInvoicesFor(Patient p) {
        return invoiceRepository.findAllByNif(p.getNif() + "");
    }

    @Override
    public List<Invoice> getPendingInvoicesFor(Patient p) {
        return invoiceRepository.findAllByNifAndPaidDate(p.getNif() + "", null);
    }

    @Override
    public List<Invoice> getPaidInvoicesFor(Patient p) {
        return invoiceRepository.findAllByNifAndPaidDateNotNull(p.getNif() + "");
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public List<Invoice> getAllPendingInvoices() {
        return invoiceRepository.findAllByPaidDate(null);
    }

    @Override
    public List<Invoice> getAllPayedInvoices() {
        return invoiceRepository.findAllByPaidDateNotNull();
    }

    @Override
    public Invoice getMostUrgentInvoiceFor(Patient p) {
        List<Invoice> invoices = getPendingInvoicesFor(p);
        if (invoices.isEmpty())
            return null;
        Invoice mostUrgent = invoices.get(0);
        for (int i = 1; i < invoices.size(); i++) {
            try {
                if (df.parse(invoices.get(i).getDueDate()).before(df.parse(mostUrgent.getDueDate()))) {
                    mostUrgent = invoices.get(i);
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
        return mostUrgent;
    }

    @Override
    public Invoice findById(String invoiceId) {
        Optional<Invoice> optional = invoiceRepository.findById(invoiceId);
        return optional.orElse(null);
    }

    /**
     * Thread may block on this method if Invoice service is not able to pay Invoice immediately.
     */
    @Override
    public void pay(Invoice invoice) throws InvoiceAlreadyPayedException {
        if (invoice == null) {
            return;
        }
        if (invoice.getPaidDate() != null) {
            throw new InvoiceAlreadyPayedException("Invoice with id " + invoice.getId() + " has already been marked as paid.");
        }
        invoice.setPaidDate(df.format(Date.valueOf(LocalDate.now())));
        invoiceRepository.save(invoice);
        InvoiceResponse response;
        String url = URL_PAY + "/" + invoice.getId();
        lock.lock();
        while (true) {
            long initialMillis = System.currentTimeMillis();
            response = restTemplate.postForObject(
                    url, new HttpEntity<>(new JSONObject().toString(), headers), InvoiceResponse.class);
            long millisPassed = System.currentTimeMillis() - initialMillis;
            if (response != null && !response.status.equals("error")) {
                break;
            }
            try {
                Thread.sleep(TIME_TO_REPEAT_INVOICE_REQUEST.toMillis() - millisPassed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lock.unlock();
    }
}

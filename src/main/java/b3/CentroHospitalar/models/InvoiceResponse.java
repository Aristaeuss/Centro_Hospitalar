package b3.CentroHospitalar.models;

public class InvoiceResponse {
    public String status;
    public Invoice invoice;

    public String getStatus() {
        return status;
    }

    public Invoice getInvoice() {
        return invoice;
    }
}

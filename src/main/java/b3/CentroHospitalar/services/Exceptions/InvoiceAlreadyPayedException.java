package b3.CentroHospitalar.services.Exceptions;

public class InvoiceAlreadyPayedException extends Throwable {
    public InvoiceAlreadyPayedException(String s) {
        super(s);
    }
}

package b3.CentroHospitalar.repositories;

import b3.CentroHospitalar.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {

    List<Invoice> findAllByNif(String nif);

    List<Invoice> findAllByNifAndPaidDate(String nif, String paidDate);

    List<Invoice> findAllByPaidDateAndIssuedDate(String paidDate, String issuedDate);

    List<Invoice> findAllByNifAndPaidDateNotNull(String nif);

    List<Invoice> findAllByPaidDate(String paidDate);

    List<Invoice> findAllByPaidDateNotNull();
}

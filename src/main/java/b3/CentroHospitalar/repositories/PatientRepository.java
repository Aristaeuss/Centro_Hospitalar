package b3.CentroHospitalar.repositories;

import b3.CentroHospitalar.models.users.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository  extends JpaRepository<Patient,Long> {


    Patient findByName(String name);
    Patient findByUsername(String username);
    Patient findByEmail(String email);
    Patient findByTelephone(int telephone);
    Patient findByNif(int nif);
    Patient findBySocialSecurity(int socialSecurity);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByNif(int nif);
    boolean existsBySocialSecurity(int socialSecurity);

    List<Patient> findByNameContaining(String name);


    boolean existsByPatientNumber(int patientNumber);
}

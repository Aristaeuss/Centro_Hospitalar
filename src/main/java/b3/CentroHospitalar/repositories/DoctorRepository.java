package b3.CentroHospitalar.repositories;

import b3.CentroHospitalar.models.users.Doctor;
import b3.CentroHospitalar.models.users.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Long> {


    boolean existsByUsername(String username);
    Doctor findByUsername(String username);
    List<Doctor> findAll();
    Doctor findByOrderNumber(int orderNumber);
    Doctor findByNif(int nif);
    List<Doctor> findAllBySpeciality(Speciality speciality);

    Doctor findByPreferredName(String preferredName);

    Doctor findByName(String doctorName);

    boolean existsByEmail(String email);

    boolean existsByNif(int nif);

    boolean existsByOrderNumber(int orderNumber);
}

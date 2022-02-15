package b3.CentroHospitalar.repositories;

import b3.CentroHospitalar.models.users.Admin;
import b3.CentroHospitalar.models.users.Doctor;
import b3.CentroHospitalar.models.users.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {


    boolean existsByUsername(String username);
    List<Admin> findAll();

    Admin findByUsername(String username);
}

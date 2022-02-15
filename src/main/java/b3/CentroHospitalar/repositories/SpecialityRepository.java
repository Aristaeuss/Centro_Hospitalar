package b3.CentroHospitalar.repositories;

import b3.CentroHospitalar.models.users.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality,Long> {


    Speciality findByName(String name);
    List<Speciality> findAll();

}

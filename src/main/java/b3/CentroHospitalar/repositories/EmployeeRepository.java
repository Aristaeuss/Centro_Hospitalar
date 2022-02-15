package b3.CentroHospitalar.repositories;

import b3.CentroHospitalar.models.users.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {


    boolean existsByUsername(String username);
    Employee findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByNif(int nif);
}

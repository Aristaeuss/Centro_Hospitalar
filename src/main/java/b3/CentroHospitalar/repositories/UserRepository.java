package b3.CentroHospitalar.repositories;

import b3.CentroHospitalar.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findByUsername(String username);
	User findByEmail(String email);


    boolean existsByEmail(String email);
}

package b3.CentroHospitalar.repositories;

import b3.CentroHospitalar.models.users.User;
import b3.CentroHospitalar.models.users.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserImageRepository extends JpaRepository<UserImage, Long> {

    UserImage findByOwner(User owner);

}

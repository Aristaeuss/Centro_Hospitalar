package b3.CentroHospitalar.repositories;

import b3.CentroHospitalar.models.users.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {


}

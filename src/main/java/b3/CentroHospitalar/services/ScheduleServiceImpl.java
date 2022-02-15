package b3.CentroHospitalar.services;

import b3.CentroHospitalar.models.users.Doctor;
import b3.CentroHospitalar.models.users.Schedule;
import b3.CentroHospitalar.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService{

    @Autowired
    ScheduleRepository scheduleRepository;

    @Override
    public void saveSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    @Override
    public void setDoctor(Doctor doctor, Schedule schedule) {
        schedule.setDoctor(doctor);
    }
}

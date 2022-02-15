package b3.CentroHospitalar.services;

import b3.CentroHospitalar.models.users.Doctor;
import b3.CentroHospitalar.models.users.Schedule;

public interface ScheduleService {

    void saveSchedule(Schedule schedule);

    void setDoctor(Doctor doctor, Schedule schedule);
}

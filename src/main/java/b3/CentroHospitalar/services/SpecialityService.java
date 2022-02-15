package b3.CentroHospitalar.services;

import b3.CentroHospitalar.models.users.Speciality;

import java.util.List;

public interface SpecialityService {

    Speciality findByName(String name);
    List<Speciality> findAll();

    boolean deleteSpecialityById(String specialityId);

    void newSpeciality(String specialityName);
}

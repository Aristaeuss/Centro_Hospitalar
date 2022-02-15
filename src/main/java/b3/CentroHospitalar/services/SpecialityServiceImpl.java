package b3.CentroHospitalar.services;

import b3.CentroHospitalar.models.users.Speciality;
import b3.CentroHospitalar.repositories.DoctorRepository;
import b3.CentroHospitalar.repositories.SpecialityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SpecialityServiceImpl implements SpecialityService{
    @Autowired
    SpecialityRepository specialityRepository;
    @Autowired
    DoctorRepository doctorRepository;

    @Override
    public Speciality findByName(String name) {
        return specialityRepository.findByName(name);
    }



    @Override
    public List<Speciality> findAll() {
        return specialityRepository.findAll();
    }

    @Override
    public boolean deleteSpecialityById(String specialityId) {
        Speciality speciality=specialityRepository.findById(Long.parseLong(specialityId)).orElse(null);
        if(doctorRepository.findAllBySpeciality(speciality).isEmpty()){
            specialityRepository.delete(speciality);
            return true;
        }
        return false;
    }

    @Override
    public void newSpeciality(String specialityName) {
        System.out.println("entrei");
        Speciality speciality=new Speciality(specialityName);
        System.out.println("criei");
        System.out.println(speciality.getName());
        specialityRepository.save(speciality);
        System.out.println("gravei");
    }
}

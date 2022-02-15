package b3.CentroHospitalar.services;

import b3.CentroHospitalar.models.ScheduledAppointment;
import b3.CentroHospitalar.models.Slot;
import b3.CentroHospitalar.models.users.*;
import b3.CentroHospitalar.repositories.DoctorRepository;
import b3.CentroHospitalar.repositories.PatientRepository;
import b3.CentroHospitalar.repositories.ScheduledAppointmentRepository;
import b3.CentroHospitalar.repositories.SlotRepository;

import b3.CentroHospitalar.services.Exceptions.IncorrectUserTypeException;
import b3.CentroHospitalar.services.Exceptions.NoSuchSlotException;
import b3.CentroHospitalar.services.Exceptions.SlotAlreadyBookedException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;


import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * A service for populating the slots of the Doctors
 */
@Service
public class SlotServiceImpl implements SlotService {

    private static final int NUM_MINUTES_PER_SLOT = Slot.NUM_MINUTES_PER_SLOT;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    SpecialityService specialityService;

    @Autowired
    UserService userService;

    @Autowired
    SlotRepository slotRepository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    ScheduledAppointmentRepository scheduledAppointmentRepository;
    /**
     * Returns the day number (1 to 7) of the week for the given LocalDate
     */
    public int weekDayNumberOfDate(LocalDate date) {
        return date.getDayOfWeek().getValue();
    }

    /**
     * Returns a YearMonth object representing the month after the current month.
     */
    public YearMonth nextMonth() {
        return YearMonth.now().plusMonths(1);
    }

    /**
     * Returns a LocalDate object representing the first date of the passed month.
     */
    public LocalDate firstDateOfMonth(YearMonth month) {
        return month.atDay(1);
    }

    /**
     * Returns an ordered List of LocalDates containing one LocalDate for each day of the passed month.
     */
    public List<LocalDate> datesOfMonth(YearMonth month) {
        LocalDate dayOne = firstDateOfMonth(month);
        int numDays = month.lengthOfMonth();
        ArrayList<LocalDate> datesOfNextMonth = new ArrayList<>(numDays);
        for (int i = 0; i < numDays; i++) {
            datesOfNextMonth.add(dayOne.plusDays(i));
        }
        return datesOfNextMonth;
    }

    /**
     * Creates slots for all doctors in the database for a given month using the doctors Schedules.
     */
    public void addSlotsForAllDoctorsForMonth(YearMonth month) {
        for (Doctor doctor : doctorRepository.findAll()) {
            addSlotsForDoctorForMonth(doctor, month);
        }
    }

    /**
     * Creates slots for a given doctor for a given month using the doctors Schedule.
     */
    public void addSlotsForDoctorForMonth(Doctor doctor, YearMonth month) {
        Schedule schedule = doctor.getSchedule();
        LocalTime morningShiftStart = schedule.getStartTime();
        LocalTime morningShitEnd = schedule.getStartLunch();
        long numMinutesMorningShift = Duration.between(morningShiftStart, morningShitEnd).toMinutes();
        LocalTime afternoonShiftStart = schedule.getEndLunch();
        LocalTime afternoonShiftEnd = schedule.getEndTime();
        long numMinutesAfternoonShift;
        if(schedule.isEndsNextDay()){
          LocalDateTime temp1=LocalDateTime.of(LocalDate.now(),afternoonShiftStart);
          LocalDateTime temp2=LocalDateTime.of(LocalDate.now().plusDays(1),afternoonShiftEnd);
          numMinutesAfternoonShift= Duration.between(temp1, temp2).toMinutes();
        }else{
        numMinutesAfternoonShift = Duration.between(afternoonShiftStart, afternoonShiftEnd).toMinutes();}
        for (LocalDate date : datesOfMonth(month)) {
            boolean shouldAddSlotsForTheDay;
            switch (weekDayNumberOfDate(date)) {
                case 1:
                    shouldAddSlotsForTheDay = schedule.isMonday();
                    break;
                case 2:
                    shouldAddSlotsForTheDay = schedule.isTuesday();
                    break;
                case 3:
                    shouldAddSlotsForTheDay = schedule.isWednesday();
                    break;
                case 4:
                    shouldAddSlotsForTheDay = schedule.isThursday();
                    break;
                case 5:
                    shouldAddSlotsForTheDay = schedule.isFriday();
                    break;
                case 6:
                    shouldAddSlotsForTheDay = schedule.isSaturday();
                    break;
                case 7:
                    shouldAddSlotsForTheDay = schedule.isSunday();
                    break;
                default:
                    shouldAddSlotsForTheDay = false;
            }
            if (shouldAddSlotsForTheDay) {
                boolean passedMidnight = false;
                for (long mins = 0; mins <= numMinutesMorningShift - NUM_MINUTES_PER_SLOT; mins += NUM_MINUTES_PER_SLOT) {
                    LocalTime time = morningShiftStart.plusMinutes(mins);
                    if (time.isBefore(LocalTime.of(0,NUM_MINUTES_PER_SLOT))) {
                        passedMidnight = true;
                    }
                    Slot slot = new Slot(LocalDateTime.of(passedMidnight ? date.plusDays(1) : date, time), doctor, doctor.getSpeciality().getName());
                    if(!doctor.getSlots().contains(slot)){
                        slotRepository.save(slot);
                        doctor.addSlot(slot);
                    }
                }
                passedMidnight = false;
                for (long mins = 0; mins <= numMinutesAfternoonShift - NUM_MINUTES_PER_SLOT; mins += NUM_MINUTES_PER_SLOT) {
                    LocalTime time = afternoonShiftStart.plusMinutes(mins);
                    if (time.isBefore(LocalTime.of(0,NUM_MINUTES_PER_SLOT))) {
                        passedMidnight = true;
                    }
                    Slot slot = new Slot(LocalDateTime.of(passedMidnight ? date.plusDays(1) : date, time), doctor, doctor.getSpeciality().getName());
                    if(!doctor.getSlots().contains(slot)){
                        slotRepository.save(slot);
                        doctor.addSlot(slot);
                    }
                }
            }
        }
        doctorRepository.save(doctor);
    }

    @Override
    public ScheduledAppointment bookSlot(Long slotId, User user) throws IncorrectUserTypeException, NoSuchSlotException, SlotAlreadyBookedException {
        Patient patient= patientRepository.findByNif(user.getNif());
        if(patient==null){
            throw new IncorrectUserTypeException("Slots can only be booked by patients");
        }
        Optional<Slot> slotOptional = slotRepository.findById(slotId);
        if (!slotOptional.isPresent()) {
            throw new NoSuchSlotException("The slot with id " + slotId + " does not exist.");
        }
        Slot slot = slotOptional.get();
        if (!slot.isAvailable()) {
            throw new SlotAlreadyBookedException("The slot is already occupied");
        }
        ScheduledAppointment scheduledAppointment= new ScheduledAppointment(slot,patient);
        scheduledAppointmentRepository.save(scheduledAppointment);
        slotRepository.save(slot);
        patientRepository.save(patient);
        return scheduledAppointment;
    }

    @Override
    public List<Integer> daysAvailableByMonthAndSpeciality(String speciality, int month) {
        List<Slot> slotsAvailable = slotRepository.findAllBySpecialityNameAndScheduledAppointment(speciality, null);
        List<Integer> datesAvailable = new ArrayList<>();
        for (Slot slot : slotsAvailable) {
            if (slot.getDateTime().getMonthValue() == month) {
                int day=slot.getDateTime().getDayOfMonth();
                if (!datesAvailable.contains(day)) {
                    datesAvailable.add(day);
                }
            }
        }
        return datesAvailable;
    }


    @Override
    public List<Integer> daysAvailableByMonthAndSpecialityThatHaveNotHappenedYet(String speciality, int month) {
        List<Slot> slotsAvailable = slotRepository.findAllBySpecialityNameAndScheduledAppointment(speciality, null);
        List<Integer> datesAvailable = new ArrayList<>();
        for (Slot slot : slotsAvailable) {
            if (slot.getDateTime().getMonthValue() == month) {
                if(!slot.getDateTime().isBefore(LocalDateTime.now())){
                    int day=slot.getDateTime().getDayOfMonth();
                    if (!datesAvailable.contains(day)) {
                        datesAvailable.add(day);
                    }
                }
            }
        }
        return datesAvailable;
    }


    @Override
    public ModelAndView daysBeforeFilter() {
        ModelAndView mav=new ModelAndView();
        Gson gson = new Gson();
        List<Integer> emptyDates = new ArrayList<>();
        emptyDates.add(0);
        String daysThis= gson.toJson(emptyDates);
        String daysNext=gson.toJson(emptyDates);
        String speciality=gson.toJson("");
        mav.addObject("jsonDates",daysThis);
        mav.addObject("jsonDatesNext",daysNext);
        mav.addObject("speciality",speciality);
        mav.addObject("jsonDoctor", gson.toJson(""));
        mav.addObject("jsonScheduledAppointmentToCancelId", gson.toJson(""));
        return mav;
    }

    @Override
    public List<Slot> slotsAvailableBySpecialityAndDate(String specialityName, LocalDate date) {
        return slotRepository.findAllBySpecialityNameAndScheduledAppointmentAndDate(specialityName, null, date);
    }

    @Override
    public List<Slot> slotsAvailableBySpecialityAndDateThatHaveNotHappenedYet(String specialityName, LocalDate date) {
        List<Slot> slots = slotsAvailableBySpecialityAndDate(specialityName, date);
        List<Slot> slotsThatHaveNotHappenedYet = new ArrayList<>();
        for(Slot slot:slots){
            if (!slot.getDateTime().isBefore(LocalDateTime.now()))
                slotsThatHaveNotHappenedYet.add(slot);
        }
        return slotsThatHaveNotHappenedYet;
    }

    @Override
    public ModelAndView getAvailabilitiesBySpeciality(String specialityName) {
        ModelAndView mav=new ModelAndView();
        List availableDaysThis=daysAvailableByMonthAndSpecialityThatHaveNotHappenedYet(specialityName, YearMonth.now().getMonthValue());
        List availableDaysNext=daysAvailableByMonthAndSpeciality(specialityName, YearMonth.now().plusMonths(1).getMonthValue());
        LocalDate today= LocalDate.now();
        List slotsForSpecialityToday=slotsAvailableBySpecialityAndDateThatHaveNotHappenedYet(specialityName,today);
        Gson gson = new Gson();
        String daysThis=gson.toJson(availableDaysThis);
        String daysNext=gson.toJson(availableDaysNext);
        String speciality=gson.toJson(specialityName);
        mav.addObject("jsonScheduledAppointmentToCancelId", gson.toJson(""));
        mav.addObject("jsonDoctor", gson.toJson(""));
        mav.addObject("speciality", speciality);
        mav.addObject("date",today);
        mav.addObject("slots",slotsForSpecialityToday);
        mav.addObject("jsonDates",daysThis);
        mav.addObject("jsonDatesNext",daysNext);
        mav.addObject("specialities",specialityService.findAll());

        return mav;
    }

    @Override
    public ModelAndView getAvailabilitiesByDoctor(int doctorOrderNumber) {
        ModelAndView mav=new ModelAndView();

        Doctor doctor=doctorRepository.findByOrderNumber(doctorOrderNumber);
        List availableDaysThis=daysAvailableByMonthAndDoctorThatHaveNotHappenedYet(doctor, YearMonth.now().getMonthValue());
        List availableDaysNext=daysAvailableByMonthAndDoctor(doctor, YearMonth.now().plusMonths(1).getMonthValue());
        LocalDate today= LocalDate.now();
        List slotsForDoctorToday =slotsAvailableByDoctorAndDateThatHaveNotHappenedYet(doctor,today);
        Gson gson = new Gson();
        String daysThis=gson.toJson(availableDaysThis);
        String daysNext=gson.toJson(availableDaysNext);
        String speciality=gson.toJson(doctor.getSpeciality().getName());
        mav.addObject("jsonScheduledAppointmentToCancelId", gson.toJson(""));
        mav.addObject("jsonDoctor", gson.toJson(doctor.getOrderNumber()));
        mav.addObject("speciality", speciality);
        mav.addObject("dateSelected",today);
        mav.addObject("slotsForDoctor",slotsForDoctorToday);
        System.out.println(slotsForDoctorToday);
        mav.addObject("jsonDates",daysThis);
        mav.addObject("jsonDatesNext",daysNext);
        mav.addObject("specialities",specialityService.findAll());

        return mav;
    }

    @Override
    public ModelAndView checkDoctorsAvailableByDateAndSpeciality(String dayMonthString) {
        ModelAndView mav=new ModelAndView();
        String[] date= dayMonthString.split(",");
        int day=Integer.parseInt(date[0]);
        int month=Integer.parseInt(date[1]);
        int year=Integer.parseInt(date[2]);
        LocalDate localDateToSearch=LocalDate.of(year,month,day);
        String specialityName= date[3];
        List availableDaysThis=daysAvailableByMonthAndSpecialityThatHaveNotHappenedYet(specialityService.findByName(specialityName).getName(), YearMonth.now().getMonthValue());
        List availableDaysNext=daysAvailableByMonthAndSpeciality(specialityService.findByName(specialityName).getName(), YearMonth.now().plusMonths(1).getMonthValue());
        Gson gson = new Gson();
        String daysThis=gson.toJson(availableDaysThis);
        String daysNext=gson.toJson(availableDaysNext);
        String speciality=gson.toJson(specialityName);
        mav.addObject("jsonScheduledAppointmentToCancelId", gson.toJson(""));
        mav.addObject("jsonDoctor", gson.toJson(""));
        mav.addObject("speciality", speciality);
        mav.addObject("jsonDates",daysThis);
        mav.addObject("jsonDatesNext",daysNext);
        List<Doctor> doctorsBySpeciality=userService.findDoctorBySpeciality(specialityService.findByName(specialityName));
        mav.addObject("doctors",doctorsBySpeciality);
        mav.addObject("date",localDateToSearch);
        List<Doctor> doctorsBySpecialityByDay=userService.findDoctorsBySpecialityByDay(doctorsBySpeciality,specialityName,localDateToSearch);
        mav.addObject("doctorsAvailableForDay",doctorsBySpecialityByDay);
        List<Slot> slotsForFirstDoctor= slotsAvailableForFirstDoctorAndDateThatHaveNotHappenedYet(doctorsBySpecialityByDay,localDateToSearch);
        mav.addObject("slotsForDoctor",slotsForFirstDoctor);
        mav.addObject("dateSelected",localDateToSearch);
        return mav;
    }

    @Override
    public ModelAndView checkSlotsAvailableByDoctorAndDate(String dateDoctor) {
        ModelAndView mav=new ModelAndView();
        String[] dSplit;
        int year;
        int day;
        int month;
        Doctor doctor;
        if(dateDoctor.contains(",")){
            //formato recebido DD,MM,YYYY,Speciality,OrderNumber
            dSplit= dateDoctor.split(",");
            day= Integer.parseInt(dSplit[0]);
            month= Integer.parseInt(dSplit[1]);
            year= Integer.parseInt(dSplit[2]);
            doctor= userService.findDoctorByOrderNumber(Integer.parseInt(dSplit[4]));
        } else if(dateDoctor.contains("T")){
            //formato recebido LocalDateTime dateDoctor=YYYY-MM-DDTHH:mm:ss:msDOrderNumber
        String[] tSplit= dateDoctor.split("T");
        dSplit= dateDoctor.split("D");
        String[] yearMonthDay= tSplit[0].split("-");
        day=Integer.parseInt(yearMonthDay[2]);
        month=Integer.parseInt(yearMonthDay[1]);
        year=Integer.parseInt(yearMonthDay[0]);
        doctor= userService.findDoctorByOrderNumber(Integer.parseInt(dSplit[1]));
        }else{
            //formato recebido LocalDate dateDoctor= YYYY-MM-DDDORDERNUMBER
            dSplit= dateDoctor.split("D");
            String[] yearMonthDay= dSplit[0].split("-");
            day=Integer.parseInt(yearMonthDay[2]);
            month=Integer.parseInt(yearMonthDay[1]);
            year=Integer.parseInt(yearMonthDay[0]);
            doctor= userService.findDoctorByOrderNumber(Integer.parseInt(dSplit[1]));
        }
        LocalDate localDateToSearch=LocalDate.of(year,month,day);
        List availableDaysThis=daysAvailableByMonthAndSpecialityThatHaveNotHappenedYet(doctor.getSpeciality().getName(), YearMonth.now().getMonthValue());
        List availableDaysNext=daysAvailableByMonthAndSpeciality(doctor.getSpeciality().getName(), YearMonth.now().plusMonths(1).getMonthValue());
        Gson gson = new Gson();
        String daysThis=gson.toJson(availableDaysThis);
        System.out.println("daysThis after pressing on specific doctor"+daysThis);
        String daysNext=gson.toJson(availableDaysNext);
        System.out.println("daysNext after pressing on specific doctor"+daysNext);
        String speciality=gson.toJson(doctor.getSpeciality().getName());
        System.out.println(speciality);
        mav.addObject("speciality", speciality);
        mav.addObject("jsonDates",daysThis);
        mav.addObject("jsonDatesNext",daysNext);
        mav.addObject("jsonScheduledAppointmentToCancelId", gson.toJson(""));
        mav.addObject("jsonDoctor", gson.toJson(doctor.getOrderNumber()));
        List<Doctor> doctorsBySpeciality=userService.findDoctorBySpeciality(doctor.getSpeciality());
        mav.addObject("date",localDateToSearch);
        System.out.println("date: "+localDateToSearch);
        List<Doctor> doctorsBySpecialityByDay=userService.findDoctorsBySpecialityByDay(doctorsBySpeciality,doctor.getSpeciality().getName(),localDateToSearch);
        mav.addObject("doctorsAvailableForDay",doctorsBySpecialityByDay);
        List<Slot> slotsForDoctor= slotsAvailableByDoctorAndDateThatHaveNotHappenedYet(doctor,localDateToSearch);
        System.out.println("Slots do medico: "+doctor+slotsForDoctor);
        mav.addObject("slotsForDoctor",slotsForDoctor);
        mav.addObject("dateSelected",localDateToSearch);
        return mav;
    }

    @Override
    public List<Slot> getSlotsForDoctorAndDate(Doctor doctor, LocalDate date) {
        List<Slot> slots=slotRepository.findAllByDoctorAndDateAndScheduledAppointment(doctor,date,null);
        return slots;
    }

    @Override
    public List<Slot> getSlotsForDoctorAndDateThatHaveNotHappenedYet(Doctor doctor, LocalDate date) {
        List<Slot> slots = getSlotsForDoctorAndDate(doctor, date);
        List<Slot> slotsThatHaveNotHappenedYet = new ArrayList<>();
        for(Slot slot:slots){
            if (!(slot.getDateTime().isBefore(LocalDateTime.now()) || slot.getDateTime().equals(LocalDate.now()) && slot.getDateTime().isBefore(LocalDateTime.now())))
                slotsThatHaveNotHappenedYet.add(slot);
        }
        return slotsThatHaveNotHappenedYet;
    }

    @Override
    public List<Slot> slotsAvailableForFirstDoctorAndDate(List doctorsAvailableTodayBySpeciality, LocalDate date) {
        Doctor doctor = null;
        if (!doctorsAvailableTodayBySpeciality.isEmpty()) {
            doctor = (Doctor) doctorsAvailableTodayBySpeciality.get(0);
        }
        return slotRepository.findAllByDoctorAndDateAndScheduledAppointment(doctor,date,null);
    }

    @Override
    public List<Slot> slotsAvailableForFirstDoctorAndDateThatHaveNotHappenedYet(List doctorsAvailableTodayBySpeciality, LocalDate date) {
        List<Slot> slots = slotsAvailableForFirstDoctorAndDate(doctorsAvailableTodayBySpeciality, date);
        List<Slot> slotsThatHaveNotHappenedYet = new ArrayList<>();
        for(Slot slot:slots){
            if (!(slot.getDateTime().isBefore(LocalDateTime.now()) || slot.getDateTime().equals(LocalDateTime.now())))
                slotsThatHaveNotHappenedYet.add(slot);
        }
        return slotsThatHaveNotHappenedYet;
    }

    private List<Slot> slotsAvailableByDoctorAndDate(Doctor doctor, LocalDate today) {
        return slotRepository.findAllByDoctorAndScheduledAppointmentAndDate(doctor,null, today);
    }

    private List<Slot> slotsAvailableByDoctorAndDateThatHaveNotHappenedYet(Doctor doctor, LocalDate today) {
        List<Slot> slots = slotsAvailableByDoctorAndDate(doctor, today);
        List<Slot> slotsThatHaveNotHappenedYet = new ArrayList<>();
        for(Slot slot:slots){
            if (!(slot.getDateTime().isBefore(LocalDateTime.now()) || slot.getDateTime().equals(LocalDateTime.now())))
                slotsThatHaveNotHappenedYet.add(slot);
        }
        return slotsThatHaveNotHappenedYet;
    }

    private List<Integer> daysAvailableByMonthAndDoctor(Doctor doctor, int monthValue) {
        List<Slot> slotsAvailable = slotRepository.findAllByDoctorAndScheduledAppointment(doctor, null);
        List<Integer> datesAvailable = new ArrayList<>();
        for (Slot slot : slotsAvailable) {
            if (slot.getDateTime().getMonthValue() == monthValue) {
                int day=slot.getDateTime().getDayOfMonth();
                if (!datesAvailable.contains(day)) {
                    datesAvailable.add(day);
                }
            }
        }
        return datesAvailable;
    }

    private List<Integer> daysAvailableByMonthAndDoctorThatHaveNotHappenedYet(Doctor doctor, int monthValue) {
        List<Slot> slotsAvailable = slotRepository.findAllByDoctorAndScheduledAppointment(doctor, null);
        List<Integer> datesAvailable = new ArrayList<>();
        for (Slot slot : slotsAvailable) {
            if (slot.getDateTime().getMonthValue() == monthValue) {
                if (!slot.getDateTime().isBefore(LocalDateTime.now())) {
                    int day = slot.getDateTime().getDayOfMonth();
                    if (!datesAvailable.contains(day)) {
                        datesAvailable.add(day);
                    }
                }
            }
        }
        return datesAvailable;
    }

    @Override
    public Slot findById(Long slotID) {
        return slotRepository.findById(slotID).orElse(null);
    }
}

package b3.CentroHospitalar.config;

import b3.CentroHospitalar.services.SlotService;
import b3.CentroHospitalar.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.persistence.Temporal;
import java.time.YearMonth;

@Configuration
@EnableAsync
@EnableScheduling
public class SlotGenerator {

    @Autowired
    SlotService slotService;


    @Scheduled(cron="* * * 1 * *")
    public void generateNextMonthSlots() {
        slotService.addSlotsForAllDoctorsForMonth(slotService.nextMonth());
    }

}

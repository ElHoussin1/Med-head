package com.example.hospital.config;
import com.example.hospital.model.Hospital;
import com.example.hospital.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Random;

@Component
public class HospitalDataLoader implements CommandLineRunner {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if database is already populated
        if (hospitalRepository.count() == 0) {
            Random random = new Random();

            // Generate 1000 random hospitals
            for (int i = 0; i < 1000; i++) {
                Hospital hospital = new Hospital();
                hospital.setName("Hospital " + i);
                hospital.setAddress(i + " Street, City");

                // Generate random coordinates within a reasonable range
                // For example, within the continental US
                hospital.setLatitude(random.nextDouble() * (49 - 25) + 25);  // Between 25 and 49
                hospital.setLongitude(random.nextDouble() * (-66 - (-125)) + (-125));  // Between -125 and -66

                hospitalRepository.save(hospital);
            }

            System.out.println("Database populated with 1000 hospitals");
        }
    }
}
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

            // Generate 1000 random hospitals in France
            for (int i = 0; i < 1000; i++) {
                Hospital hospital = new Hospital();
                hospital.setName("Hospital " + i);
                hospital.setAddress(i + " Rue, Ville");

                // Generate random coordinates within France
                hospital.setLatitude(random.nextDouble() * (51.0 - 41.0) + 41.0);  // Between 41 and 51
                hospital.setLongitude(random.nextDouble() * (9.0 - (-5.0)) + (-5.0));  // Between -5 and 9

                hospitalRepository.save(hospital);
            }

            System.out.println("Database populated with 1000 hospitals in France");
        }
    }
}

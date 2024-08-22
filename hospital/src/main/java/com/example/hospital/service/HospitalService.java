package com.example.hospital.service;

import com.example.hospital.model.Hospital;
import com.example.hospital.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    public List<Hospital> findNearestHospitals(double lat, double lon, int limit) {
        List<Hospital> allHospitals = hospitalRepository.findAll();

        return allHospitals.stream()
                .map(hospital -> {
                    double distance = calculateDistance(lat, lon, hospital.getLatitude(), hospital.getLongitude());
                    return new HospitalDistance(hospital, distance);
                })
                .sorted((h1, h2) -> Double.compare(h1.getDistance(), h2.getDistance()))
                .limit(limit)
                .map(HospitalDistance::getHospital)
                .collect(Collectors.toList());
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Haversine formula
        double R = 6371; // Earth's radius in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }

    public static class HospitalDistance {
    private final Hospital hospital;
    private final double distance;

    public HospitalDistance(Hospital hospital, double distance) {
        this.hospital = hospital;
        this.distance = distance;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public double getDistance() {
        return distance;
    }
}

}
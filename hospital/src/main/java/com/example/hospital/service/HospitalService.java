package com.example.hospital.service;

import com.example.hospital.model.Hospital;
import com.example.hospital.model.Department;
import com.example.hospital.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalService {
    @Autowired
    private HospitalRepository hospitalRepository;

    public Hospital findNearestHospitalWithAvailability(double userLat, double userLon, String departmentName) {
        List<Hospital> hospitals = hospitalRepository.findByDepartmentName(departmentName);
        Hospital nearestHospital = null;
        double minDistance = Double.MAX_VALUE;

        for (Hospital hospital : hospitals) {
            Department department = hospital.getDepartments().stream()
                    .filter(d -> d.getName().equals(departmentName))
                    .findFirst()
                    .orElse(null);

            if (department != null && department.getAvailableBeds() > 0) {
                double distance = calculateDistance(userLat, userLon, hospital.getLatitude(), hospital.getLongitude());
                if (distance < minDistance) {
                    minDistance = distance;
                    nearestHospital = hospital;
                }
            }
        }
        return nearestHospital;
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Haversine formula to calculate the distance
        final int R = 6371; // Radius of the Earth in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // convert to kilometers
    }
}
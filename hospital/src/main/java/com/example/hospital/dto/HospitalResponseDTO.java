package com.example.hospital.dto;

import com.example.hospital.model.Hospital;
import com.example.hospital.model.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HospitalResponseDTO {
    private Long id;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private int availableBeds;
    private double distance;

    public HospitalResponseDTO(Hospital hospital, String departmentName, double distance) {
        this.id = hospital.getId();
        this.name = hospital.getName();
        this.address = hospital.getAddress();
        this.latitude = hospital.getLatitude();
        this.longitude = hospital.getLongitude();
        this.distance = distance;

        Department department = hospital.getDepartments().stream()
                .filter(d -> d.getName().equals(departmentName))
                .findFirst()
                .orElse(null);

        this.availableBeds = department != null ? department.getAvailableBeds() : 0;
    }

    // Getters and setters...
}
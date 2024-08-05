package com.example.hospital.dto;

import com.example.hospital.model.Hospital;
import com.example.hospital.model.Department;

public class HospitalResponseDTO {
    private String name;
    private String address;
    private String departmentName;
    private int availableBeds;
    private double distance;
    private boolean canReserve;

    public HospitalResponseDTO(Hospital hospital, String departmentName) {
        this.name = hospital.getName();
        this.address = hospital.getAddress();
        this.departmentName = departmentName;

        Department department = hospital.getDepartments().stream()
                .filter(d -> d.getName().equals(departmentName))
                .findFirst()
                .orElse(null);

        if (department != null) {
            this.availableBeds = department.getAvailableBeds();
            this.canReserve = this.availableBeds > 0;
        }

        // Calculate distance here if needed
    }

    // Getters and setters
    // (existing getters and setters)

    public boolean isCanReserve() {
        return canReserve;
    }

    public void setCanReserve(boolean canReserve) {
        this.canReserve = canReserve;
    }
}

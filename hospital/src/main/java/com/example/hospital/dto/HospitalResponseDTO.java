package com.example.hospital.dto;

import com.example.hospital.model.Hospital;
import com.example.hospital.model.Department;

public class HospitalResponseDTO {
    private String name;
    private String address;
    private String departmentName;
    private int availableBeds;
    private double distance;

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
        }

        // Calculate distance here if needed
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getAvailableBeds() {
        return availableBeds;
    }

    public void setAvailableBeds(int availableBeds) {
        this.availableBeds = availableBeds;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
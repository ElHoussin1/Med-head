package com.example.hospital.controller;

import com.example.hospital.dto.HospitalResponseDTO;
import com.example.hospital.exception.HospitalNotFoundException;
import com.example.hospital.exception.NoBedAvailableException;
import com.example.hospital.model.Hospital;
import com.example.hospital.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/hospitals")
@Validated
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @GetMapping("/search")
    public ResponseEntity<List<HospitalResponseDTO>> searchHospitals(
            @RequestParam @Min(0) double lat,
            @RequestParam @Min(0) double lon,
            @RequestParam @NotBlank String departmentName,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size) {

        Page<Hospital> hospitals = hospitalService.findNearestHospitalsWithAvailability(lat, lon, departmentName, page, size);
        List<HospitalResponseDTO> response = hospitals.getContent().stream()
                .map(hospital -> new HospitalResponseDTO(hospital, departmentName))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/reserve")
    public ResponseEntity<String> reserveBed(
            @RequestParam @Min(1) Long hospitalId,
            @RequestParam @Min(1) Long departmentId) {
        try {
            hospitalService.reserveBed(hospitalId, departmentId);
            return ResponseEntity.ok("Bed reserved successfully");
        } catch (HospitalNotFoundException | NoBedAvailableException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    public ResponseEntity<String> handleValidationExceptions(javax.validation.ConstraintViolationException e) {
        return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
    }
}
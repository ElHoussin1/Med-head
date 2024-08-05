package com.example.hospital.controller;

import com.example.hospital.dto.HospitalResponseDTO;
import com.example.hospital.model.Hospital;
import com.example.hospital.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hospitals")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @GetMapping("/search")
    public ResponseEntity<HospitalResponseDTO> searchHospital(@RequestParam double lat,
                                                              @RequestParam double lon,
                                                              @RequestParam String departmentName) {
        Hospital hospital = hospitalService.findNearestHospitalWithAvailability(lat, lon, departmentName);
        if (hospital != null) {
            HospitalResponseDTO response = new HospitalResponseDTO(hospital, departmentName);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/reserve")
    public ResponseEntity<Void> reserveBed(@RequestParam Long hospitalId,
                                           @RequestParam Long departmentId) {
        boolean success = hospitalService.reserveBed(hospitalId, departmentId);
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(409).build();
        }
    }
}

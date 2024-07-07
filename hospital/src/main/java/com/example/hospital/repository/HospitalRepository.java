package com.example.hospital.repository;

import com.example.hospital.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    @Query("SELECT h FROM Hospital h JOIN h.departments d WHERE d.name = :departmentName")
    List<Hospital> findByDepartmentName(String departmentName);
}
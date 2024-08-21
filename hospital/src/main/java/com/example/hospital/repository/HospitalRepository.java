package com.example.hospital.repository;

import com.example.hospital.model.Hospital;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {

    @Query("SELECT DISTINCT h FROM Hospital h " +
            "JOIN h.departments d " +
            "WHERE d.name = :departmentName " +
            "AND d.availableBeds > 0")
    Page<Hospital> findByDepartmentNameWithAvailableBeds(@Param("departmentName") String departmentName,
                                                         Pageable pageable);

    // Existing methods...

    @Query("SELECT h FROM Hospital h JOIN h.departments d WHERE d.name = :departmentName")
    List<Hospital> findByDepartmentName(@Param("departmentName") String departmentName);
}
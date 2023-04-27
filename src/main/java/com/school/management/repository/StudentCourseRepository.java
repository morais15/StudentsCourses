package com.school.management.repository;

import com.school.management.model.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {

    List<StudentCourse> findByStudent_Id(Long id);
    List<StudentCourse> findByCourse_Id(Long id);
    Boolean existsByStudent_Id(Long id);
    Boolean existsByCourse_Id(Long id);
}

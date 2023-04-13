package com.school.management.service;

import com.school.management.model.Course;
import com.school.management.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public List<Course> getCourses(boolean withoutStudents) {
        if (withoutStudents)
            return courseRepository
                    .findAll()
                    .stream()
                    .filter(c -> c.getStudents() == null || c.getStudents().isEmpty())
                    .toList();

        return courseRepository.findAll();
    }

    public Course findById(Long id) {
        return courseRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));
    }
}

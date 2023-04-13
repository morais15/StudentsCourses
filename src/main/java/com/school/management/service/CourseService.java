package com.school.management.service;

import com.school.management.model.Course;
import com.school.management.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;
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

    public Course getCourse(Long id) {
        return courseRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));
    }

    public Course updateCourse(Course newCourse) {
        var course = getCourse(newCourse.getId());

        if (newCourse.getName() != null && !newCourse.getName().isBlank() && !newCourse.getName().equals(course.getName())) {
            course.setName(newCourse.getName());
            course.setUpdatedAt(Timestamp.from(Instant.now()));
            course = courseRepository.save(course);
        }

        return course;
    }
}

package com.school.management.service;

import com.school.management.model.Course;
import com.school.management.model.Student;
import com.school.management.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Set;

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

    public Set<Student> getStudentsFromCourse(Long id) {
        return this.getCourse(id).getStudents();
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

    public Course createCourse(Course course) {
        var timestamp = Timestamp.from(Instant.now());
        course.setCreatedAt(timestamp);
        course.setUpdatedAt(timestamp);

        return courseRepository.save(course);
    }

    public List<Course> createCourses(List<Course> courseList) {
        return courseList
                .stream()
                .map(this::createCourse)
                .toList();
    }

    public void deleteCourse(Long id, Boolean confirmDeletion) {
        if (!confirmDeletion)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "To delete the course and student-courses relationships, inform confirm-deletion=true as a query param."
            );
        var course = this.getCourse(id);
        courseRepository.delete(course);
    }

    public void deleteAllCourses(Boolean confirmDeletion) {
        if (!confirmDeletion)
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "To delete ALL courses and students-courses relationships, inform confirm-deletion=true as a query param."
            );

        courseRepository.deleteAll();
    }
}

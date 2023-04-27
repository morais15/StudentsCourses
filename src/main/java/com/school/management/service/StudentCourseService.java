package com.school.management.service;

import com.school.management.model.Course;
import com.school.management.model.Student;
import com.school.management.model.StudentCourse;
import com.school.management.repository.StudentCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentCourseService {
    private final StudentCourseRepository studentCourseRepository;
    private final CourseService courseService;
    private final StudentService studentService;

    public List<StudentCourse> findAll() {
        return studentCourseRepository.findAll();
    }

    public List<StudentCourse> findByCourseId(Long id) {
        return studentCourseRepository.findByCourse_Id(id);
    }

    public List<StudentCourse> findByStudentId(Long id) {
        return studentCourseRepository.findByStudent_Id(id);
    }

    public Boolean existsByStudentId(Long id) {
        return studentCourseRepository.existsByStudent_Id(id);
    }

    public Boolean existsByCourseId(Long id) {
        return studentCourseRepository.existsByCourse_Id(id);
    }

    public List<Course> getCoursesWithoutStudents() {
        var courseIds = courseService
                .getCourses()
                .stream()
                .map(Course::getId)
                .toList();

        return courseIds
                .stream()
                .filter(id -> !existsByCourseId(id))
                .map(courseService::getCourse)
                .toList();
    }

    public List<Student> getStudentsWithOutCourses() {
        var studentIds = studentService
                .getStudents()
                .stream()
                .map(Student::getId)
                .toList();

        return studentIds
                .stream()
                .filter(id -> !existsByStudentId(id))
                .map(studentService::getStudent)
                .toList();
    }

    public List<Student> getStudentsFromCourse(Long id) {
        courseService.getCourse(id);

        return this
                .findByCourseId(id)
                .stream()
                .map(StudentCourse::getStudent)
                .toList();
    }

    public List<Course> getCoursesFromStudent(Long id) {
        studentService.getStudent(id);

        return this
                .findByStudentId(id)
                .stream()
                .map(StudentCourse::getCourse)
                .toList();
    }

    public List<StudentCourse> updateCourseStudents(Long id, List<Long> studentIds) {
        var course = courseService.getCourse(id);
        var students = studentIds
                .stream()
                .map(studentService::getStudent)
                .toList();

        return students
                .stream()
                .map(student -> studentCourseRepository.save(new StudentCourse(student, course)))
                .toList();
    }

    public List<StudentCourse> updateStudentCourses(Long id, List<Long> courseIds) {
        var student = studentService.getStudent(id);
        var courses = courseIds
                .stream()
                .map(courseService::getCourse)
                .toList();

        return courses
                .stream()
                .map(course -> studentCourseRepository.save(new StudentCourse(student, course)))
                .toList();
    }
}

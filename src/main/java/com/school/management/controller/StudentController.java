package com.school.management.controller;

import com.school.management.model.Course;
import com.school.management.model.Student;
import com.school.management.model.StudentCourse;
import com.school.management.model.dto.StudentDto;
import com.school.management.service.StudentCourseService;
import com.school.management.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final StudentCourseService studentCourseService;

    /**
     * GET methods (retrieving info)
     */

    /**
     * HTTP method: GET
     * <p>
     * //@param withoutCourses = true --> return the list of students without any course (default: false).
     *
     * @return the list of students.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StudentDto> getStudents(@RequestParam(name = "without-courses") Optional<Boolean> withoutCourses) {
        List<Student> students;
        if (withoutCourses.isPresent())
            students = studentCourseService.getStudentsWithOutCourses();
        else
            students = studentService.getStudents();

        return students
                .stream()
                .map(StudentDto::new)
                .toList();
    }

    /**
     * HTTP method: GET
     *
     * @param id = the student id.
     * @return student info related to the id.
     */
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentDto getStudent(@PathVariable Long id) {
        var student = studentService.getStudent(id);
        return new StudentDto(student);
    }

    /**
     * PUT methods (updating info)
     */

    /**
     * HTTP method: PUT
     *
     * @param id         = the student id.
     * @param studentDto = JSON containing the student's name and address to be updated.
     *                   Ex: {"name":"John Doe", "address": "Some address"}
     * @return the student's info updated.
     */
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentDto updateStudent(@PathVariable Long id, @RequestBody StudentDto studentDto) {
        studentDto.setId(id);
        return new StudentDto(studentService.updateStudent(new Student(studentDto)));
    }

    /**
     * POST methods (inserting info)
     */

    /**
     * HTTP method: POST
     *
     * @param studentDtoList = a list of students, in JSON format, to be registered. Limited to 50 students per request.
     *                       Ex: [{"name": "John Doe", "address": "Some address"},
     *                       {"name": "Jane Doe", "address": "Another address"}]
     * @return a list of the students that were registered with the submitted request.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<StudentDto> createStudents(@RequestBody List<StudentDto> studentDtoList) {
        var students = studentDtoList
                .stream()
                .map(Student::new)
                .toList();

        return studentService
                .createStudents(students)
                .stream()
                .map(StudentDto::new)
                .toList();
    }

    /**
     * DELETE methods (removing info)
     */

    /**
     * HTTP method: DELETE
     *
     * @param confirmDeletion = true --> deletes all the students, and student-courses relationships.
     *                        The course table will not be modified.  (default: false)
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudents(@RequestParam(name = "confirm-deletion") Optional<Boolean> confirmDeletion) {
        studentService.deleteAllStudents(confirmDeletion.orElse(false));
    }

    /**
     * HTTP method: DELETE
     *
     * @param confirmDeletion = true --> deletes the student, and student-courses relationships.
     *                        The course table will not be modified.  (default: false)
     * @param id              = the student id.
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable Long id, @RequestParam(name = "confirm-deletion") Optional<Boolean> confirmDeletion) {
        studentService.deleteStudent(id, confirmDeletion.orElse(false));
    }

    /**
     * HTTP method: GET
     * <p>
     *
     * @param id = the student id.
     * @return list of courses the student is enrolled.
     */
    @GetMapping(value = "/{id}/courses")
    @ResponseStatus(HttpStatus.OK)
    public List<Course> getCoursesFromStudent(@PathVariable Long id) {
        return studentCourseService.getCoursesFromStudent(id);
    }

    /**
     * HTTP method: GET
     * <p>
     *
     * @return list of relationships between students and courses, ordered by student and course.
     */
    @GetMapping(value = "/courses")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentCourse> getRelations() {
        return studentCourseService.findAll();
    }

    /**
     * HTTP method: PUT
     * <p>
     *
     * @param id        = the student id.
     * @param courseIds = the ids of the courses to enroll the student. Limited to 5 courses.
     *                  Ex: [1, 2, 3]
     * @return a list containing the student id and the enrolled courses.
     */
    @PutMapping(value = "/{id}/courses")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentCourse> updateStudentCourses(@PathVariable Long id, @RequestBody List<Long> courseIds) {
        return studentCourseService.updateStudentCourses(id, courseIds);
    }
}

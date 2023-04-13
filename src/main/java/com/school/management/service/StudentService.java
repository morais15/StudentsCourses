package com.school.management.service;


import com.school.management.model.Student;
import com.school.management.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public List<Student> getStudents(boolean withoutStudents) {
        if (withoutStudents)
            return studentRepository
                    .findAll()
                    .stream()
                    .filter(s -> s.getCourses() == null || s.getCourses().isEmpty())
                    .toList();

        return studentRepository
                .findAll();
    }

    public Student getStudent(Long id) {
        return studentRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found."));
    }

    @Transactional
    public Student updateStudent(Student newStudent) {
        Student student = this.getStudent(newStudent.getId());

        boolean updated = false;
        if (newStudent.getName() != null && !newStudent.getName().isBlank() && !newStudent.getName().equals(student.getName())) {
            student.setName(newStudent.getName());
            updated = true;
        }
        if (newStudent.getAddress() != null && !newStudent.getAddress().isBlank() && !newStudent.getAddress().equals(student.getAddress())) {
            student.setAddress(newStudent.getAddress());
            updated = true;
        }

        if (updated) {
            student.setUpdatedAt(Timestamp.from(Instant.now()));
            student = studentRepository.save(student);
        }
        return student;
    }

    public List<Student> createStudents(List<Student> students) {
        if (students.size() > 50) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "A request can not contain more than 50 students.");
        }

        Timestamp ts = Timestamp.from(Instant.now());
        return studentRepository
                .saveAll(
                        students
                                .stream()
                                .filter(s -> s.getName() != null && !s.getName().isBlank() && s.getAddress() != null && !s.getAddress().isBlank())
                                .toList()
                );
    }

    @Transactional
    public void deleteAllStudents(Boolean confirmDeletion) {
        if (confirmDeletion) {
            studentRepository.deleteAll();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "To delete ALL students and students-courses relationships, inform confirm-deletion=true as a query param.");
        }
    }

    @Transactional
    public void deleteStudent(Long id, Boolean confirmDeletion) {
        if (confirmDeletion) {
            Student student = studentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Student not found."));
            studentRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "To delete the student and student-courses relationships, inform confirm-deletion=true as a query param.");
        }


    }
}

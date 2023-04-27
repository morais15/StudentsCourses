package com.school.management.model;

import com.school.management.model.dto.StudentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    @OneToMany
    private Set<StudentCourse> courses;

    public Student(StudentDto studentDto) {
        this.id = studentDto.getId();
        this.name = studentDto.getName();
        this.address = studentDto.getAddress();
        this.createdAt = studentDto.getCreatedAt();
        this.updatedAt = studentDto.getUpdatedAt();
        this.courses = studentDto.getCourses();
    }
}

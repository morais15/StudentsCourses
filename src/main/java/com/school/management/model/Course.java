package com.school.management.model;

import com.school.management.model.dto.CourseDto;
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
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Student> students;

    public Course(CourseDto courseDto) {
        this.id = courseDto.getId();
        this.name = courseDto.getName();
        this.createdAt = courseDto.getCreatedAt();
        this.updatedAt = courseDto.getUpdatedAt();
    }
}

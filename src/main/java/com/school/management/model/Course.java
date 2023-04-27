package com.school.management.model;

import com.school.management.model.dto.CourseDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

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

    public Course(CourseDto courseDto) {
        this.id = courseDto.getId();
        this.name = courseDto.getName();
        this.createdAt = courseDto.getCreatedAt();
        this.updatedAt = courseDto.getUpdatedAt();
    }
}

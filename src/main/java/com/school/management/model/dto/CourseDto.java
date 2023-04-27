package com.school.management.model.dto;

import com.school.management.model.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {
    private Long id;
    private String name;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public CourseDto(Course course) {
        this.id = course.getId();
        this.name = course.getName();
        this.createdAt = course.getCreatedAt();
        this.updatedAt = course.getUpdatedAt();
    }
}

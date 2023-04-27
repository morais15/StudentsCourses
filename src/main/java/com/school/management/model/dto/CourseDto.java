package com.school.management.model.dto;

import com.school.management.model.Course;
import com.school.management.model.StudentCourse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {
    private Long id;
    private String name;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Set<StudentCourse> students;

    public CourseDto(Course course) {
        this.id = course.getId();
        this.name = course.getName();
        this.createdAt = course.getCreatedAt();
        this.updatedAt = course.getUpdatedAt();
        this.students = course.getStudents();
    }
}

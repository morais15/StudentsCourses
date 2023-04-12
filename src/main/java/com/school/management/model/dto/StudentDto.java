package com.school.management.model.dto;

import com.school.management.model.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {
    private Long id;
    private String name;
    private String address;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public StudentDto(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.address = student.getAddress();
        this.createdAt = student.getCreatedAt();
        this.updatedAt = student.getUpdatedAt();
    }
}

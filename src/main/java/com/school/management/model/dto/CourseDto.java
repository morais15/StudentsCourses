package com.school.management.model.dto;

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
    private String address;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}

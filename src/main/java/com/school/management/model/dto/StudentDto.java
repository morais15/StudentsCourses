package com.school.management.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
public class StudentDto {

    private Long id;

    private String name;

    private String address;

    private Timestamp createdAt;

    private Timestamp updatedAt;


    public StudentDto() {
    }

    public StudentDto(String name, String address) {
        Timestamp ts = Timestamp.from(Instant.now());
        this.id = 0L;
        this.name = name;
        this.address = address;
        this.createdAt = ts;
        this.updatedAt = ts;
    }

    public StudentDto(Long id, String name, String address, Timestamp createdAt, Timestamp updatedAt) {
        this(name, address);
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

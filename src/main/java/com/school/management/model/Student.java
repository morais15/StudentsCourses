package com.school.management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Student() {
    }

    public Student(Long id) {
        this.id = id;
    }

    public Student(String name, String address, Timestamp createdAt, Timestamp updatedAt) {
        this.name = name;
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Student(Long id, String name, String address, Timestamp createdAt, Timestamp updatedAt) {
        this(name, address, createdAt, updatedAt);
        this.id = id;
    }
}

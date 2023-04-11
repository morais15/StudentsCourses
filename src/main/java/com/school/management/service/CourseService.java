package com.school.management.service;

import com.school.management.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
}

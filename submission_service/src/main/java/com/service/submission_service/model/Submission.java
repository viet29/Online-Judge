package com.service.submission_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "submissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "output")
    private String output;

    @Column(name = "actual_time")
    private Double actualTime;

    @Column(name = "actual_memory")
    private Integer actualMemory;

    @Column(name = "language", nullable = false)
    private String language;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "file_content",nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "date_submit", nullable = false)
    @CreationTimestamp
    private LocalDateTime dateSubmit;

    @Column(name = "exercise_id", nullable = false)
    private Long exerciseId;

}

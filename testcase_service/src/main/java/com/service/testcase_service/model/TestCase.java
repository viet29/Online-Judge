package com.service.testcase_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "testcases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "input")
    private String input;

    @Column(name = "output", nullable = false)
    private String output;

    @Column(name = "exercise_id", nullable = false)
    private Long exerciseId;

    @Column(name = "is_hidden", nullable = false)
    private Boolean isHidden;
}

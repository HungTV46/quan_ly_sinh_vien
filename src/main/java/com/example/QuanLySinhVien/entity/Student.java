package com.example.QuanLySinhVien.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "STUDENT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq")
    @SequenceGenerator(name = "student_seq", sequenceName = "STUDENT_SEQ",  allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @NonNull
    @Column(name = "USERNAME", unique = true)
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @NotBlank(message = "Tên bắt buộc phải điền")
    @Column(name = "NAME")
    private String name;

    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;

    @Column(name = "MARK")
    private Double mark;

    @Schema(hidden = true)
    @ManyToMany(mappedBy = "Students")
    private Set<ClassEntity> classEntities;
}

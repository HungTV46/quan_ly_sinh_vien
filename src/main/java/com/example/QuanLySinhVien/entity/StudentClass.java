package com.example.QuanLySinhVien.entity;

import com.example.QuanLySinhVien.entity.embedded.StudentClassId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "student_class")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentClass implements Serializable {
    @EmbeddedId
    private StudentClassId id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("classEntityId")
    @JoinColumn(name = "class_id")
    private ClassEntity classEntity;

    private LocalDateTime createdAt;
    private Integer status; // 1: đang học, 0: nghỉ học
}

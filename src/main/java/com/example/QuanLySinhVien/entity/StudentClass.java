package com.example.QuanLySinhVien.entity;

import com.example.QuanLySinhVien.entity.embedded.StudentClassId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private Integer status; // 1: đang học, 0: nghỉ học
}

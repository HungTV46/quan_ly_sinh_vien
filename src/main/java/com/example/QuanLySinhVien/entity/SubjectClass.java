package com.example.QuanLySinhVien.entity;

import com.example.QuanLySinhVien.entity.embedded.SubjectClassId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "SUBJECT_CLASS")
public class SubjectClass {

    @EmbeddedId
    private SubjectClassId id;

    @ManyToOne
    @MapsId("classEntityId")
    @JoinColumn(name = "class_id")
    private ClassEntity classEntity;

    @ManyToOne
    @MapsId("subjectId")
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private Integer status; // 1: active, 2: inactive
}

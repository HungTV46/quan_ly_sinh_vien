package com.example.QuanLySinhVien.mapper;

import com.example.QuanLySinhVien.dto.request.StudentClassRequest;
import com.example.QuanLySinhVien.dto.request.SubjectClassRequest;
import com.example.QuanLySinhVien.dto.response.StudentClassResponse;
import com.example.QuanLySinhVien.dto.response.SubjectClassResponse;
import com.example.QuanLySinhVien.entity.StudentClass;
import com.example.QuanLySinhVien.entity.SubjectClass;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentClassMapper {

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "student.name", target = "studentName", defaultValue = "")
    @Mapping(source = "classEntity.id", target = "classId")
    @Mapping(source = "classEntity.className", target = "className", defaultValue = "")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    StudentClassResponse toDto(StudentClass entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "classEntity", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    StudentClass toEntity(StudentClassRequest request);

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "student.name", target = "studentName", defaultValue = "")
    @Mapping(source = "classEntity.id", target = "classId")
    @Mapping(source = "classEntity.className", target = "className", defaultValue = "")
    List<StudentClassResponse> toListDto(List<StudentClass> entity);

    @Mapping(target = "id", ignore = true)
    void update(StudentClassRequest request, @MappingTarget StudentClass entity);
}

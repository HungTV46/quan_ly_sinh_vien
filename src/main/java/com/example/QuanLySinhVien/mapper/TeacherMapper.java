package com.example.QuanLySinhVien.mapper;

import com.example.QuanLySinhVien.dto.request.TeacherRequest;
import com.example.QuanLySinhVien.dto.response.TeacherResponse;
import com.example.QuanLySinhVien.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    TeacherResponse toDto(Teacher entity);

    Teacher toEntity(TeacherRequest request);

    List<TeacherResponse> toListDto(List<Teacher> entity);

    @Mapping(target = "id", ignore = true)
    void updateTeacher(TeacherRequest request, @MappingTarget Teacher entity);
}

package com.example.QuanLySinhVien.mapper;

import com.example.QuanLySinhVien.dto.request.SubjectRequest;
import com.example.QuanLySinhVien.dto.response.SubjectResponse;
import com.example.QuanLySinhVien.entity.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
    SubjectResponse toDto(Subject entity);

    Subject toEntity(SubjectRequest request);

    List<SubjectResponse> toListDto(List<Subject> entity);

    @Mapping(target = "id", ignore = true)
    void updateSubject(SubjectRequest request, @MappingTarget Subject entity);
}

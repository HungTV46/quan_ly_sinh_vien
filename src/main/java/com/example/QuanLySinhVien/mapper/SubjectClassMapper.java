package com.example.QuanLySinhVien.mapper;

import com.example.QuanLySinhVien.dto.request.SubjectClassRequest;
import com.example.QuanLySinhVien.dto.response.SubjectClassResponse;
import com.example.QuanLySinhVien.entity.SubjectClass;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubjectClassMapper {
    SubjectClassResponse toDto(SubjectClass entity);

    SubjectClass toEntity(SubjectClassRequest request);

    List<SubjectClassResponse> toListDto(List<SubjectClass> entity);

    @Mapping(target = "id", ignore = true)
    void update(SubjectClassRequest request, @MappingTarget SubjectClass entity);
}

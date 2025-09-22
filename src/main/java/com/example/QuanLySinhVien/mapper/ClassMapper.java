package com.example.QuanLySinhVien.mapper;

import com.example.QuanLySinhVien.dto.request.ClassRequest;
import com.example.QuanLySinhVien.dto.response.ClassResponse;
import com.example.QuanLySinhVien.entity.ClassEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClassMapper {
    ClassEntity toEntity(ClassRequest classRequest);

    ClassResponse toDto(ClassEntity clazz);

    List<ClassResponse> toListDto(List<ClassEntity> clazz);

    @Mapping(target = "id",  ignore = true)
    void updateClass(ClassRequest classRequest, @MappingTarget ClassEntity clazz);
}

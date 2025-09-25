package com.example.QuanLySinhVien.mapper;

import com.example.QuanLySinhVien.constant.Constant;
import com.example.QuanLySinhVien.dto.PageDto;
import com.example.QuanLySinhVien.dto.request.StudentRequest;
import com.example.QuanLySinhVien.dto.response.StudentResponse;
import com.example.QuanLySinhVien.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    Student toEntity(StudentRequest studentRequest);

    StudentResponse toDto(Student student);

//    Optional<StudentResponse> toDtoOptional(Optional<Student> student);

    List<StudentResponse> toListDto(List<Student> students);

    @Mapping(target = "id", ignore = true)
    void updateStudent(StudentRequest request, @MappingTarget Student student);
}

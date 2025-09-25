package com.example.QuanLySinhVien.service;

import com.example.QuanLySinhVien.dto.request.SearchTeacherRequest;
import com.example.QuanLySinhVien.dto.request.TeacherRequest;
import com.example.QuanLySinhVien.dto.response.TeacherResponse;
import com.example.QuanLySinhVien.entity.ClassEntity;
import com.example.QuanLySinhVien.entity.Teacher;
import com.example.QuanLySinhVien.exception.AppException;
import com.example.QuanLySinhVien.exception.ErrorCode;
import com.example.QuanLySinhVien.mapper.TeacherMapper;
import com.example.QuanLySinhVien.repository.ClassRepository;
import com.example.QuanLySinhVien.repository.TeacherRepository;
import com.example.QuanLySinhVien.service.specification.TeacherSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final ClassRepository classRepository;
    private final TeacherMapper teacherMapper;

    public TeacherResponse create(TeacherRequest request) {
        if (teacherRepository.existsByEmail(request.getEmail())){
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        ClassEntity classEntity = classRepository.findById(request.getClassId())
                .orElseThrow(()-> new AppException(ErrorCode.ID_NOTFOUND));
        Teacher teacher = teacherMapper.toEntity(request);
        teacher.setClassEntity(classEntity);
        teacherRepository.save(teacher);
        return teacherMapper.toDto(teacher);
    }

    public List<TeacherResponse> getAll() {
        List<Teacher> teachers = teacherRepository.findAll();
        return teacherMapper.toListDto(teachers);
    }

    public TeacherResponse getById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.ID_NOTFOUND));
        return teacherMapper.toDto(teacher);
    }

    public TeacherResponse update(Long id, TeacherRequest request) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.ID_NOTFOUND));

        ClassEntity classEntity = classRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.ID_NOTFOUND));

        teacherMapper.updateTeacher(request, teacher);
        teacher.setClassEntity(classEntity);
        teacherRepository.save(teacher);
        return teacherMapper.toDto(teacher);
    }

    public void delete(Long id) {
        teacherRepository.deleteById(id);
    }

    public Page<TeacherResponse> search(SearchTeacherRequest request){
        Page<Teacher> teachers = teacherRepository.findAll(
                Specification.where(TeacherSpecification.hasContent(request.getContent()))
                .and(TeacherSpecification.hasClassId(request.getClassId()))
                .and(TeacherSpecification.hasEmail(request.getEmail()))
                .and(TeacherSpecification.hasPhone(request.getPhone())),
                request.getPageDto().toPageable()
        );

        return teachers.map(teacherMapper::toDto);
    }
}

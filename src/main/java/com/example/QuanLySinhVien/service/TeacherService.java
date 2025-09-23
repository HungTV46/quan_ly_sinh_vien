package com.example.QuanLySinhVien.service;

import com.example.QuanLySinhVien.dto.request.TeacherRequest;
import com.example.QuanLySinhVien.dto.response.TeacherResponse;
import com.example.QuanLySinhVien.entity.Teacher;
import com.example.QuanLySinhVien.exception.AppException;
import com.example.QuanLySinhVien.exception.ErrorCode;
import com.example.QuanLySinhVien.mapper.TeacherMapper;
import com.example.QuanLySinhVien.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    public TeacherResponse create(TeacherRequest request) {
        if (teacherRepository.existsByEmail(request.getEmail())){
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        Teacher teacher = teacherMapper.toEntity(request);
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

        teacherMapper.updateTeacher(request, teacher);
        teacherRepository.save(teacher);
        return teacherMapper.toDto(teacher);
    }

    public void delete(Long id) {
        teacherRepository.deleteById(id);
    }
}

package com.example.QuanLySinhVien.service;

import com.example.QuanLySinhVien.dto.request.StudentClassRequest;
import com.example.QuanLySinhVien.dto.response.StudentClassResponse;
import com.example.QuanLySinhVien.entity.ClassEntity;
import com.example.QuanLySinhVien.entity.Student;
import com.example.QuanLySinhVien.entity.StudentClass;
import com.example.QuanLySinhVien.entity.embedded.StudentClassId;
import com.example.QuanLySinhVien.exception.AppException;
import com.example.QuanLySinhVien.exception.ErrorCode;
import com.example.QuanLySinhVien.mapper.StudentClassMapper;
import com.example.QuanLySinhVien.repository.ClassRepository;
import com.example.QuanLySinhVien.repository.StudentClassRepository;
import com.example.QuanLySinhVien.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentClassService {
    private final StudentClassRepository studentClassRepository;
    private final ClassRepository classRepository;
    private final StudentRepository studentRepository;
    private final StudentClassMapper studentClassMapper;

    @Transactional
    public StudentClassResponse create(StudentClassRequest request) {
        ClassEntity classEntity = classRepository.findById(request.getClassId())
                .orElseThrow(()-> new AppException(ErrorCode.ID_NOTFOUND,"classId"));

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(()-> new AppException(ErrorCode.ID_NOTFOUND,"studentId"));

        StudentClassId id = new StudentClassId( request.getStudentId(), request.getClassId());

        if (studentClassRepository.existsById(id)) {
            throw new AppException(ErrorCode.ID_EXISTED, "ClassId and StudentId");
        }

        if (classEntity.getCurrentCapacity() >= classEntity.getMaxCapacity()){
            throw new AppException(ErrorCode.CLASS_FULL);
        }
        classEntity.setCurrentCapacity(classEntity.getCurrentCapacity() + 1);

        StudentClass entity = new StudentClass(new StudentClassId(request.getStudentId(),
                request.getClassId()),
                student,classEntity,
                request.getStatus()
                );

        StudentClass saved = studentClassRepository.save(entity);
        classRepository.save(classEntity);
        return studentClassMapper.toDto(saved);
    }

    public List<StudentClassResponse> getAll() {
        List<StudentClass> listEntity = studentClassRepository.findAll();
        return studentClassMapper.toListDto(listEntity);
    }

    public List<StudentClassResponse> getByClassId(Long classId) {
        return studentClassMapper.toListDto(studentClassRepository.findByClassEntityId(classId));
    }
    public StudentClassResponse getById(Long studentId, Long classId) {
        StudentClass entity = studentClassRepository.findById(new StudentClassId(studentId, classId))
                .orElseThrow(()-> new AppException(ErrorCode.ID_NOTFOUND, "StudentId and classId"));
        return studentClassMapper.toDto(entity);
    }

    public StudentClassResponse update(StudentClassRequest request) {
        StudentClass entity = studentClassRepository.findByClassIdAndStudentId(request.getClassId(), request.getStudentId());
        if(entity == null) {
            throw new AppException(ErrorCode.ID_NOTFOUND, "StudentId or classId");
        }

        entity.setStatus(request.getStatus());
        studentClassRepository.save(entity);
        return studentClassMapper.toDto(entity);
    }

    public void delete(Long studentId, Long classId) {
        studentClassRepository.deleteById(new  StudentClassId(studentId, classId));
    }
}

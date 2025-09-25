package com.example.QuanLySinhVien.service;

import com.example.QuanLySinhVien.dto.request.SubjectClassRequest;
import com.example.QuanLySinhVien.dto.response.StudentClassResponse;
import com.example.QuanLySinhVien.dto.response.SubjectClassResponse;
import com.example.QuanLySinhVien.entity.ClassEntity;
import com.example.QuanLySinhVien.entity.Subject;
import com.example.QuanLySinhVien.entity.SubjectClass;
import com.example.QuanLySinhVien.entity.embedded.StudentClassId;
import com.example.QuanLySinhVien.entity.embedded.SubjectClassId;
import com.example.QuanLySinhVien.exception.AppException;
import com.example.QuanLySinhVien.exception.ErrorCode;
import com.example.QuanLySinhVien.mapper.SubjectClassMapper;
import com.example.QuanLySinhVien.repository.ClassRepository;
import com.example.QuanLySinhVien.repository.SubjectClassRepository;
import com.example.QuanLySinhVien.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectClassService {
    private final SubjectClassRepository subjectClassRepository;
    private final SubjectRepository subjectRepository;
    private final ClassRepository classRepository;
    private final SubjectClassMapper subjectClassMapper;

    public SubjectClassResponse create(SubjectClassRequest request) {
        ClassEntity classEntity = classRepository.findById(request.getClassId())
                .orElseThrow(() -> new AppException(ErrorCode.ID_NOTFOUND, "ClassId"));

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new AppException(ErrorCode.ID_NOTFOUND, "SubjectId"));

        if (subjectClassRepository.existsById(new SubjectClassId(request.getSubjectId(), request.getClassId()))){
            throw new AppException(ErrorCode.ID_EXISTED, "SubjectClassId");
        }

        SubjectClass entity = new SubjectClass();
        entity.setId(new SubjectClassId());
        entity.setSubject(subject);
        entity.setClassEntity(classEntity);
        entity.setStatus(request.getStatus());

        SubjectClass saved = subjectClassRepository.save(entity);

        return subjectClassMapper.toDto(saved);
    }

    public List<SubjectClassResponse> getAll() {
        List<SubjectClass> listEntity = subjectClassRepository.findAll();
        return subjectClassMapper.toListDto(listEntity);
    }

    public List<SubjectClassResponse> getByClassId(Long classId) {
        return subjectClassMapper.toListDto(subjectClassRepository.findByClassEntityId(classId));
    }

    public SubjectClassResponse getById(Long subjectId,  Long classId) {

        SubjectClass entity = subjectClassRepository.findById(new SubjectClassId(subjectId,classId))
                .orElseThrow(()-> new AppException(ErrorCode.ID_NOTFOUND, "SubjectId and classId"));
        return subjectClassMapper.toDto(entity);
    }

    public SubjectClassResponse update(SubjectClassRequest request) {
        SubjectClass entity = subjectClassRepository.findByClassIdAndSubjectId(request.getClassId(), request.getSubjectId());
        if (entity == null){
            throw new AppException(ErrorCode.ID_NOTFOUND);
        }
        entity.setStatus(request.getStatus());

        subjectClassRepository.save(entity);

        return subjectClassMapper.toDto(entity);
    }

    public void delete(Long subjectId,  Long classId) {
        subjectClassRepository.deleteById(new SubjectClassId(subjectId,classId));
    }
}

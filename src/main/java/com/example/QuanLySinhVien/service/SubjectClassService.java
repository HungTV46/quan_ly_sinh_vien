package com.example.QuanLySinhVien.service;

import com.example.QuanLySinhVien.dto.request.SubjectClassRequest;
import com.example.QuanLySinhVien.dto.response.SubjectClassResponse;
import com.example.QuanLySinhVien.entity.SubjectClass;
import com.example.QuanLySinhVien.exception.AppException;
import com.example.QuanLySinhVien.exception.ErrorCode;
import com.example.QuanLySinhVien.mapper.SubjectClassMapper;
import com.example.QuanLySinhVien.repository.SubjectClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectClassService {
    private final SubjectClassRepository subjectClassRepository;
    private final SubjectClassMapper subjectClassMapper;

    public SubjectClassResponse create(SubjectClassRequest request) {
        if (subjectClassRepository.existsByClassEntityId(request.getClassId()) && subjectClassRepository.existsBySubjectId(request.getSubjectId())){
            throw new AppException(ErrorCode.ID_EXISTED);
        }
        SubjectClass entity = subjectClassMapper.toEntity(request);
        subjectClassRepository.save(entity);
        return subjectClassMapper.toDto(entity);
    }

    public List<SubjectClassResponse> getAll() {
        List<SubjectClass> listEntity = subjectClassRepository.findAll();
        return subjectClassMapper.toListDto(listEntity);
    }

    public SubjectClassResponse getById(Long id) {
        SubjectClass entity = subjectClassRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.ID_NOTFOUND));
        return subjectClassMapper.toDto(entity);
    }

    public SubjectClassResponse update(Long id, SubjectClassRequest request) {
        SubjectClass entity = subjectClassRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.ID_NOTFOUND));

        subjectClassMapper.update(request, entity);
        subjectClassRepository.save(entity);
        return subjectClassMapper.toDto(entity);
    }

    public void delete(Long id) {
        subjectClassRepository.deleteById(id);
    }
}

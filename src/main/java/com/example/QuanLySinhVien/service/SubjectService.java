package com.example.QuanLySinhVien.service;

import com.example.QuanLySinhVien.dto.request.SubjectRequest;
import com.example.QuanLySinhVien.dto.response.SubjectResponse;
import com.example.QuanLySinhVien.entity.Subject;
import com.example.QuanLySinhVien.exception.AppException;
import com.example.QuanLySinhVien.exception.ErrorCode;
import com.example.QuanLySinhVien.mapper.SubjectMapper;
import com.example.QuanLySinhVien.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    public SubjectResponse createSubject(SubjectRequest request) {
        if (subjectRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }
        Subject subject = subjectMapper.toEntity(request);
        subjectRepository.save(subject);
        return subjectMapper.toDto(subject);
    }

    public List<SubjectResponse> getAllSubjects() {
        List<Subject> subjects = subjectRepository.findAll();
        return subjectMapper.toListDto(subjects);
    }

    public SubjectResponse getById(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.ID_NOTFOUND));
        return subjectMapper.toDto(subject);
    }

    public SubjectResponse updateSubject(Long id, SubjectRequest request) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.ID_NOTFOUND));

        subjectMapper.updateSubject(request, subject);
        subjectRepository.save(subject);
        return subjectMapper.toDto(subject);
    }

    public void delete(Long id) {
        subjectRepository.deleteById(id);
    }
}

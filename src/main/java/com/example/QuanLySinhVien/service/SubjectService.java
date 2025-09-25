package com.example.QuanLySinhVien.service;

import com.example.QuanLySinhVien.dto.request.SearchSubjectRequest;
import com.example.QuanLySinhVien.dto.request.SubjectRequest;
import com.example.QuanLySinhVien.dto.response.SubjectClassSearchResponse;
import com.example.QuanLySinhVien.dto.response.SubjectResponse;
import com.example.QuanLySinhVien.dto.response.SubjectSearchResponse;
import com.example.QuanLySinhVien.entity.Subject;
import com.example.QuanLySinhVien.exception.AppException;
import com.example.QuanLySinhVien.exception.ErrorCode;
import com.example.QuanLySinhVien.mapper.SubjectMapper;
import com.example.QuanLySinhVien.repository.SubjectRepository;
import com.example.QuanLySinhVien.service.specification.SubjectSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    public SubjectResponse create(SubjectRequest request) {
        if (subjectRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }
        Subject entity = subjectMapper.toEntity(request);
        subjectRepository.save(entity);
        return subjectMapper.toDto(entity);
    }

    public List<SubjectResponse> getAll() {
        List<Subject> subjects = subjectRepository.findAll();
        return subjectMapper.toListDto(subjects);
    }

    public SubjectResponse getById(Long id) {
        Subject entity = subjectRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.ID_NOTFOUND));
        return subjectMapper.toDto(entity);
    }

    public SubjectResponse update(Long id, SubjectRequest request) {
        Subject entity = subjectRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.ID_NOTFOUND));

        subjectMapper.update(request, entity);
        subjectRepository.save(entity);
        return subjectMapper.toDto(entity);
    }

    public void delete(Long id) {
        subjectRepository.deleteById(id);
    }

    public List<SubjectSearchResponse> search(SearchSubjectRequest request) {
        List<Subject> subjects = subjectRepository.findAll(
                Specification.where(SubjectSpecification.hasName(request.getName()))
                        .and(SubjectSpecification.filterByStatus(request.getStatus()))
                        .and(SubjectSpecification.getByClassId(request.getClassId()))
        );

        return subjects.stream()
                .map(subject -> {
                    SubjectSearchResponse response = new SubjectSearchResponse();
                    response.setName(subject.getName());
                    response.setId(subject.getId());

                    List<SubjectClassSearchResponse> classResponses = subject.getSubjectClasses().stream()
                            .filter(sc -> request.getStatus() == null || sc.getStatus().equals(request.getStatus()))
                            .map(sc -> {
                                SubjectClassSearchResponse classResponse = new SubjectClassSearchResponse();
                                classResponse.setClassId(sc.getClassEntity().getId());
                                classResponse.setClassName(sc.getClassEntity().getClassName());
                                classResponse.setStatus(sc.getStatus());
                                return classResponse;
                            })
                            .toList();
                    response.setClasses(classResponses);
                    return response;
                })
                .toList();
    }
}

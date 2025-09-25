package com.example.QuanLySinhVien.service;

import com.example.QuanLySinhVien.dto.request.ClassRequest;
import com.example.QuanLySinhVien.dto.request.SearchClassRequest;
import com.example.QuanLySinhVien.dto.response.ClassResponse;
import com.example.QuanLySinhVien.entity.ClassEntity;
import com.example.QuanLySinhVien.exception.AppException;
import com.example.QuanLySinhVien.exception.ErrorCode;
import com.example.QuanLySinhVien.mapper.ClassMapper;
import com.example.QuanLySinhVien.repository.ClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClassService {
    private final ClassRepository classRepository;
    private final ClassMapper classMapper;

    public ClassResponse createClass(ClassRequest classRequest) {
        if(classRepository.existsByClassName(classRequest.getClassName())) {
            throw new AppException(ErrorCode.CLASSNAME_EXISTED);
        }
        ClassEntity classEntity1 = classMapper.toEntity(classRequest);
        classRepository.save(classEntity1);

        return classMapper.toDto(classEntity1);
    }

    public Page<ClassResponse> getAllClasses(Pageable pageable) {
        return classRepository.findAll(pageable).map(classMapper::toDto);
    }

    public ClassResponse getClassById(Long id) {
        ClassEntity classEntity1 = classRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ID_NOTFOUND, "ClassId"));
        return classMapper.toDto(classEntity1);
    }

    public ClassResponse updateClassById(Long id, ClassRequest classRequest) {
        ClassEntity classEntity1 = classRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ID_NOTFOUND, "ClassId"));

        if (classRepository.existsByClassName(classRequest.getClassName())) {
            throw new AppException(ErrorCode.CLASSNAME_EXISTED);
        }
        classMapper.updateClass(classRequest, classEntity1);
        classRepository.save(classEntity1);
        return classMapper.toDto(classEntity1);
    }

    public void deleteClassById(Long id) {
        classRepository.deleteById(id);
    }

    public Page<ClassResponse> findByClassName(SearchClassRequest searchClassRequest) {
        return classRepository.findByClassName(searchClassRequest.getClassName(),
                searchClassRequest.getPageDto().toPageable(Sort.by("c.className").descending()))
                .map(classMapper::toDto);
    }
}

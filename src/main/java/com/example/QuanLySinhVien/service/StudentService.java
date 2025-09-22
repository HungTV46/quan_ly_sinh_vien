package com.example.QuanLySinhVien.service;

import com.example.QuanLySinhVien.dto.request.MarkFilterRequest;
import com.example.QuanLySinhVien.dto.request.StudentRequest;
import com.example.QuanLySinhVien.dto.response.StudentResponse;
import com.example.QuanLySinhVien.entity.Student;
import com.example.QuanLySinhVien.exception.AppException;
import com.example.QuanLySinhVien.exception.ErrorCode;
import com.example.QuanLySinhVien.exception.UsernameAlreadyExistsException;
import com.example.QuanLySinhVien.mapper.StudentMapper;
import com.example.QuanLySinhVien.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public StudentResponse createStudent(StudentRequest request) {
        if (studentRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }

        Student student = studentMapper.toEntity(request);
        StudentResponse response = studentMapper.toDto(studentRepository.save(student));

        return response;
    }

    public List<StudentResponse> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentResponse> studentResponses = studentMapper.toListDto(students);
        return studentResponses;
    }

    public StudentResponse getStudentById(Long id) {
        return studentMapper.toDto(studentRepository.findById(id).orElseThrow(()-> new RuntimeException("student is not exist")));
    }

    public List<StudentResponse> filterMarkGoodStudent(MarkFilterRequest request) {
        List<Student> students = studentRepository.findAll().stream()
                .filter(s -> {
                    if ("<".equals(request.getOperator())) {
                        return s.getMark() < request.getMark();
                    } else if (">".equals(request.getOperator())) {
                        return s.getMark() > request.getMark();
                    } else if ("<=".equals(request.getOperator())) {
                        return s.getMark() <= request.getMark();
                    } else if (">=".equals(request.getOperator())) {
                        return s.getMark() >= request.getMark();
                    } else if ("==".equals(request.getOperator())) {
                        return Objects.equals(s.getMark(), request.getMark());
                    } else {
                        throw new IllegalArgumentException("Operator không hợp lệ" + request.getOperator());
                    }
                })
                .sorted(Comparator.comparing(Student::getMark).reversed())
                .collect(Collectors.toList());
        return studentMapper.toListDto(students);
    }

    public List<StudentResponse> sortStudents(List<String> sort) {
        List<String> normalized = normalizeTokens(sort);
        List<Sort.Order> orders = new ArrayList<>();
        for (String s : normalized) {
            String[] split = s.split(",");
            orders.add(new Sort.Order(Sort.Direction.fromString(split.length > 1 ? split[1] : "asc"), split[0]));

        }
        return studentRepository.findAll(Sort.by(orders))
                .stream()
                .map(studentMapper::toDto)
                .toList();
    }

    @Bean
    private List<String> normalizeTokens(List<String> tokens) {
        List<String> out = new ArrayList<>();
        for (int i = 0; i < tokens.size(); i++) {
            String t = tokens.get(i).trim();
            if (t.isEmpty()) continue;

            // if contains comma already -> treat as single token
            if (t.contains(",")) {
                out.add(t);
                continue;
            }

            String lower = t.toLowerCase(Locale.ROOT);
            // if token is just direction -> attach to previous
            if (lower.equals("asc") || lower.equals("desc")) {
                if (out.isEmpty()) {
                    // direction with no field before -> ignore (or throw)
                    continue;
                } else {
                    int last = out.size() - 1;
                    String prev = out.get(last);
                    if (!prev.contains(",")) {
                        out.set(last, prev + "," + lower);
                    } else {
                        // prev already has direction -> keep prev, ignore this extra dir
                    }
                }
                continue;
            }

            // token looks like a field without comma. Check next token: if next is asc/desc, combine.
            if (i + 1 < tokens.size()) {
                String next = tokens.get(i + 1).trim();
                String nlow = next.toLowerCase(Locale.ROOT);
                if (nlow.equals("asc") || nlow.equals("desc")) {
                    out.add(t + "," + nlow);
                    i++; // skip next because consumed as direction
                    continue;
                }
            }
        }

        return out;
    }

    public List<StudentResponse> getStudentByName(String name) {
        return studentMapper.toListDto(studentRepository.findStudentByName(name.toUpperCase(Locale.ROOT)));
    }

    public StudentResponse updateStudent(StudentRequest request, Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("không tìm thấy thông tin sinh viên"));
        studentMapper.updateStudent(request, student);
        Student studentNew = studentRepository.save(student);
        return studentMapper.toDto(studentNew);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public List<StudentResponse> searchStudentByMark(String status) {
        List<Student> student = studentRepository.findAll();
        if (status.equals("highest")) {
            student = student.stream()
                    .sorted(Comparator.comparing(Student::getMark).reversed())
                    .limit(1)
                    .collect(Collectors.toList());
        } else if (status.equals("lowest")) {
            student = student.stream()
                    .sorted(Comparator.comparing(Student::getMark))
                    .limit(1)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("lỗi kiểu tìm kiêm");
        }
        return studentMapper.toListDto(student);
    }

    public List<StudentResponse> findStudentByYear(Integer year){
        List<Student> students = studentRepository.findStudentByYear(year)
                .stream()
                .sorted(Comparator.comparing(Student::getName))
                .collect(Collectors.toList());
        return studentMapper.toListDto(students);
    }

    public Double getAverageMark() {
        List<Student> students = studentRepository.findAll();

        Double avgMark = students.stream()
                .mapToDouble(Student::getMark)
                .average()
                .orElse(0.0);
        return Math.round(avgMark * 100.0) / 100.0;
    }

    public List<StudentResponse> getStudentByClassId(Long classId) {
        List<Student> students = studentRepository.findStudentByClassId(classId);
        return studentMapper.toListDto(students);
    }
}

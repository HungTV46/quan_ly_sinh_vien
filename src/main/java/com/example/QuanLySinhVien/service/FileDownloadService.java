package com.example.QuanLySinhVien.service;

import com.example.QuanLySinhVien.entity.UploadedFile;
import com.example.QuanLySinhVien.repository.UploadedFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class FileDownloadService {
    private final UploadedFileRepository uploadedFileRepository;
    private static final String UPLOAD_DIR = "D:\\spring\\QuanLySinhVien\\uploads";
//    private final List list;

    public List<String> downloadFile(List<String> urls){
        ExecutorService executorService = Executors.newFixedThreadPool(urls.size());
        List<Future<String>> futures = new ArrayList<>();
        for (int i = 0; i < urls.size(); i++) {
            String url = urls.get(i);
            int index = i + 1;
            Callable<String> task = () -> {
                try (InputStream in = new URL(url).openStream()) {
                    String fileName = extractFileName(url, index);
                    Files.copy(in, Paths.get(fileName), StandardCopyOption.REPLACE_EXISTING);
                    return "tải thành công file name: " + fileName;
                } catch (Exception e) {
                    return "lỗi khi tải file " + index + ": " + e.getMessage();
                }
            };
            futures.add(executorService.submit(task));
        }
        List<String> results = new ArrayList<>();
        for (Future<String> future : futures) {
            try {
                results.add(future.get());
            } catch (Exception e) {
                results.add("Task error: " + e.getMessage());
            }
        }
        executorService.shutdown();
        return results;
    }

    private String extractFileName(String url, int index) {
        try {
            String path = new URL(url).getPath();
            String name = path.substring(path.lastIndexOf("/") + 1);
            if (name.isEmpty()) {
                return "file" + index + ".dat";
            }
            return name;
        } catch (MalformedURLException e) {
            return "file" + index + ".dat";
        }
    }

    public List<String> uploadFile(MultipartFile file, List<MultipartFile> files) {
        List<String> results = new ArrayList<>();

        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        Optional.ofNullable(file)
                .filter(f -> !f.isEmpty())
                .ifPresent(f -> insertFile(f,uploadDir,results));

        if (!files.isEmpty() && files != null) {
            files.stream()
                    .filter(f -> !f.isEmpty())
                    .forEach(f -> insertFile(f,uploadDir,results));
        }
        return results;
    }

    private void insertFile(MultipartFile file, File uploadDir, List<String> results) {
        UploadedFile uploadedFile = new UploadedFile();
        uploadedFile.setFileName(file.getOriginalFilename());
        uploadedFile.setFileType(file.getContentType());
        uploadedFile.setFileSize(file.getSize());
        uploadedFile.setUploadTime(LocalDateTime.now());

        File dest = new File(uploadDir, uploadedFile.getFileName());

        uploadedFile.setFilePath(dest.getAbsolutePath());
        uploadedFileRepository.save(uploadedFile);
        try {
            file.transferTo(dest);
            results.add("Saved file: " + uploadedFile.getFileName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

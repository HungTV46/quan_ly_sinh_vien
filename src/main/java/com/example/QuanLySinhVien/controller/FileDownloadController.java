package com.example.QuanLySinhVien.controller;

import com.example.QuanLySinhVien.service.FileDownloadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/file")
public class FileDownloadController {
    public final FileDownloadService fileDownloadService;

    @PostMapping("/download-file")
    public ResponseEntity<?> multiDownloadFile(@RequestBody List<String> urls) {
        return ResponseEntity.ok(fileDownloadService.downloadFile(urls));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> multiUploadFile(@RequestPart("file") MultipartFile file, @RequestPart("files") List<MultipartFile> files) {
        return ResponseEntity.ok(fileDownloadService.uploadFile(file, files));
    }
}

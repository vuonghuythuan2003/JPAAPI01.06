package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ra.service.UploadFileService;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/uploadFile")
public class UploadFileController {
    @Autowired
    private UploadFileService uploadFileService;

    @PostMapping("upload")
    public ResponseEntity<String> upload(@RequestParam MultipartFile file) throws IOException {
        String fileName = uploadFileService.uploaFile(file);
        return new ResponseEntity<>(fileName, HttpStatus.CREATED);
    }
}

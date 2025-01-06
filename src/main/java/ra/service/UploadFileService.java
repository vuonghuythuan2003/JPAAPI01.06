package ra.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadFileService {
    String uploaFile(MultipartFile file) throws IOException;
}

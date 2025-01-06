package ra.service.imp;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ra.service.UploadFileService;

import java.io.IOException;
import java.util.Map;

@Service
public class UploadFileServiceImp implements UploadFileService {
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String uploaFile(MultipartFile file) throws IOException {
        // Kiểm tra file không null và không rỗng
        if (file == null || file.isEmpty()) {
            throw new IOException("File upload không được để trống.");
        }

        String fileName = file.getOriginalFilename();
        if (fileName == null || !fileName.contains(".")) {
            throw new IOException("Tên file không hợp lệ. File phải có đuôi mở rộng.");
        }

        // Kiểm tra và xử lý đuôi file
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        if (!extension.matches("jpg|jpeg|png")) {
            throw new IOException("Định dạng file không hợp lệ. Chỉ chấp nhận JPG, JPEG, PNG.");
        }

        // Loại bỏ đuôi file trong tên
        fileName = fileName.substring(0, fileName.lastIndexOf("."));

        // Upload file lên Cloudinary
        Map uploadParams = ObjectUtils.asMap(
                "public_id", fileName,
                "folder", "products_images"
        );
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), uploadParams);

        // Trả về URL của file
        return uploadResult.get("url").toString();
    }
}

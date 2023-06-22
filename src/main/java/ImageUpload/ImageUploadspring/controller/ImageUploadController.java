package ImageUpload.ImageUploadspring.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ImageUploadController {

    private Map<String, byte[]> uploadedImages = new HashMap<>();

    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "Please select a file to upload";
        }

        try {
            // 업로드된 이미지를 메모리에 저장
            byte[] imageData = file.getBytes();
            String fileName = file.getOriginalFilename();
            uploadedImages.put(fileName, imageData);

            // 업로드된 파일의 URL 반환
            String fileUrl = "/image/" + fileName;
            return fileUrl;
        } catch (IOException e) {
            e.printStackTrace();
            return "Upload failed";
        }
    }

    @GetMapping("/image/{fileName:.+}")
    public byte[] getImage(@PathVariable String fileName) {
        return uploadedImages.get(fileName);
    }
}

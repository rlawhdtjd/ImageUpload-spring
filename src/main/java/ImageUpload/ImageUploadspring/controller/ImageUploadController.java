package ImageUpload.ImageUploadspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.tomcat.util.codec.binary.Base64;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class ImageUploadController {

    private final AtomicInteger idCounter = new AtomicInteger();
    private final ConcurrentHashMap<Integer, String> imageStore = new ConcurrentHashMap<>();

    @PostMapping("/uploadImage")
    @ResponseBody
    public int uploadImage(@RequestParam("image") MultipartFile image) {
        try {
            String base64Image = Base64.encodeBase64String(image.getBytes());
            int id = idCounter.incrementAndGet();
            imageStore.put(id, base64Image);
            return id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @GetMapping("/image/{id}")
    public String displayImage(@PathVariable int id, Model model) {
        model.addAttribute("image", imageStore.get(id));
        return "displayImage";
    }
}

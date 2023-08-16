package ss18.demouploadfile.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/upload")
@PropertySource("classpath:upload.properties")
public class UploadController {
    @GetMapping
    public String upload() {
        return "form";
    }

    @Value("${uploadPath}")
    private String uploadPath;

    @PostMapping("/image")
    // Đây là một annotation của Spring được sử dụng để ánh xạ phương thức này
    // với một URL cụ thể và phương thức HTTP POST.


    // upload 1 file
//    public String handleUpload(@RequestParam ("image") MultipartFile multipartFile, Model model){
//        // tiến hành upload vào thư mục chỉ định là image
//        File file  = new File(uploadPath);
//        if(file.exists()){
//           // chưa tồn tại folder, create new folder
//            file.mkdirs();
//        }
//        // lấy tệp đích
//        String fileName = multipartFile.getOriginalFilename();
//        // upload ảnh
//        try {
//            FileCopyUtils.copy(multipartFile.getBytes(),new File(uploadPath+fileName));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        model.addAttribute("uploadImage",fileName);
//        return "home";
//    }
    // upload nhiều file
    public String handleUpload(@RequestParam("image") List<MultipartFile> multipartFiles, Model model) {
        // tiến hành upload vào thư mục chỉ định là image
        File file = new File(uploadPath);
        if (file.exists()) {
            // chưa tồn tại folder, create new folder
            file.mkdirs();
        }
        // lấy tệp đích, vì là upload nhiều file nên dùng
        List<String> listImageURLs = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            //  Lấy tên gốc của tệp tin.
            String fileName = multipartFile.getOriginalFilename();
            // Thêm tên tệp tin vào danh sách listImageURLs.
            listImageURLs.add(fileName);
            try {
                // Tiến hành tải lên tệp tin bằng cách sử dụng
                FileCopyUtils.copy(multipartFile.getBytes(), new File(uploadPath + fileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            model.addAttribute("uploadList", listImageURLs);
        }
        return "home";
    }
}

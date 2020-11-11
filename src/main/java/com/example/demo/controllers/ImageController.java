package com.example.demo.controllers;

import com.example.demo.models.ImageBlob;
import com.example.demo.repositories.ImageRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/img")
public class ImageController {
    ImageRepository imageRepository = new ImageRepository();

    @RequestMapping(value = {"/",""})
    public String img(Model m){
        List<ImageBlob> list = imageRepository.getAllImages();
        m.addAttribute("images",list);
        return "img";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("asBlob") String asBlob, RedirectAttributes attributes){
        if(Boolean.parseBoolean(asBlob)){
            imageRepository.uploadImageToDatabase(file);
        }
        else {
            imageRepository.uploadAsPath(file);
        }
        return "redirect:/img";
    }
}
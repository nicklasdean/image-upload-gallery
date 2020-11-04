package com.example.demo.controllers;

import com.example.demo.models.ImageBlob;
import com.example.demo.repositories.ImageRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ImageController {
    ImageRepository imageRepository = new ImageRepository();

    @PostMapping("/uploadImg")
    public String handleFileUpload(@RequestParam("img") MultipartFile file, RedirectAttributes attributes){
        imageRepository.uploadImageToDatabase(file);
        return "img";
    }

    @GetMapping("/img")
    public String img(Model m){
        List<ImageBlob> list = imageRepository.getAllImages();
        m.addAttribute("images",list);
        return "img";
    }
}
package com.baeldung.mongodb.web;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.baeldung.mongodb.models.Photo;
import com.baeldung.mongodb.services.PhotoService;

@Controller
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @GetMapping("/photos/{id}")
    public String getPhoto(@PathVariable String id, Model model) {
        Photo photo = photoService.getPhoto(id);
        if (photo != null) {
            model.addAttribute("title", photo.getTitle());
            model.addAttribute("image", Base64.getEncoder().encodeToString(photo.getImage().getData()));
            return "photos";
        }
        model.addAttribute("message", "Photo not found");
        return "index";
    }

    @GetMapping("/photos/upload")
    public String uploadPhoto(Model model) {
        model.addAttribute("message", "hello");
        return "uploadPhoto";
    }

    @PostMapping("/photos/add")
    public String addPhoto(@RequestParam("title") String title, @RequestParam("image") MultipartFile image, Model model) {
        String id = photoService.addPhoto(title, image);
        if (id == null) {
            model.addAttribute("message", "Error Occurred");
            return "index";
        }
        return "redirect:/photos/" + id;
    }
}

package com.baeldung.mongodb.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.baeldung.mongodb.models.Video;
import com.baeldung.mongodb.services.VideoService;

@Controller
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping("/videos/{id}")
    public String getVideo(@PathVariable String id, Model model) {
        Video video = videoService.getVideo(id);
        if (video != null) {
            model.addAttribute("title", video.getTitle());
            model.addAttribute("url", "/videos/stream/" + id);
            return "videos";
        }
        model.addAttribute("message", "Video not found");
        return "index";
    }

    @GetMapping("/videos/stream/{id}")
    public void streamVideo(@PathVariable String id, HttpServletResponse response) {
        Video video = videoService.getVideo(id);
        if (video != null) {
            try {
                FileCopyUtils.copy(video.getStream(), response.getOutputStream());
            } catch (IOException e) {
                response.setStatus(500);
            }
        } else {
            response.setStatus(404);
        }
    }

    @GetMapping("/videos/upload")
    public String uploadVideo(Model model) {
        model.addAttribute("message", "hello");
        return "uploadVideo";
    }

    @PostMapping("/videos/add")
    public String addVideo(@RequestParam("title") String title, @RequestParam("file") MultipartFile file, Model model) {
        String id = videoService.addVideo(title, file);
        if (id == null) {
            model.addAttribute("message", "Error Occurred");
            return "index";
        }
        return "redirect:/videos/" + id;
    }
}

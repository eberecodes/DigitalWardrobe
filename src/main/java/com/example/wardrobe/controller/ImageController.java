package com.example.wardrobe.controller;

import com.example.wardrobe.model.Image;
import com.example.wardrobe.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

    @GetMapping("/images/all")
    public List<Image> fetchAllProducts() {
        return this.imageService.fetchAllImages();
    }

    @PostMapping("/images/add")
    public String addImage(@RequestParam("title") String title, @RequestParam("image") MultipartFile image, Model model)
            throws IOException {
        String id = imageService.addImage(title, image);
        return "redirect:/images/" + id;
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable String id) {
        Image image = imageService.getImage(id);

        if (image != null) {
            MediaType mediaType = MediaType.valueOf(image.getContentType());
            byte[] imageData = image.getImage().getData();
            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(imageData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

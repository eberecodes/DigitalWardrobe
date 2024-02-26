package com.example.wardrobe.service;

import com.example.wardrobe.model.Image;
import com.example.wardrobe.repository.ImageRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepo;

    public String addImage(String title, MultipartFile file) throws IOException {
        Image image = new Image(title);
        image.setId(UUID.randomUUID().toString());
        image.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        image.setContentType(file.getContentType());
        image = imageRepo.insert(image);

        return image.getId();
    }

    public Image getImage(String id) {
        return imageRepo.findById(id).get();
    }

    public List<Image> fetchAllImages(){
        return this.imageRepo.findAll();
    }
}


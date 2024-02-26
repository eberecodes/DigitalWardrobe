package com.example.wardrobe.repository;

import com.example.wardrobe.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ImageRepository extends MongoRepository<Image, String> {
    @Query("{id:'?0'}")
    Image findImageById(String name);
}

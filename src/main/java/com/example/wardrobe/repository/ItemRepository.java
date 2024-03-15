package com.example.wardrobe.repository;

import com.example.wardrobe.model.Image;
import com.example.wardrobe.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;

public interface ItemRepository extends MongoRepository<Item, String> {

    @Query("{title:'?0'}")
    Item findItemByTitle(String title);

    @Query(value="{category:'?0'}", fields="{'title' : 1, 'images' : 1}")
    List<Item> findAllInCategory(String category);

    @Query("{'id' : ?0}")
    @Update("{ $set: { 'images': ?1} }")
    Integer updateItem(String id, List<Image> images);

    long count();

}

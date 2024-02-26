package com.example.wardrobe.service;

import com.example.wardrobe.model.Image;
import com.example.wardrobe.model.Item;
import com.example.wardrobe.repository.ImageRepository;
import com.example.wardrobe.repository.ItemRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepo;

    public String createItem(String title, String size, String brand, String category) {
        Item item = new Item(title);
        item.setId(UUID.randomUUID().toString());
        item.setCategory(category);
        item.setSize(size);
        item.setBrand(brand);
        item = itemRepo.insert(item);

        return item.getId();
    }

    public boolean addImageToItem(Image image, Item item) {
        if (item == null || image == null) {
            return false;
        }

        try {
            String id = item.getId();
            List<Image> images = item.getImages();
            images.add(image);
            item.setImages(images);
            itemRepo.updateItem(id, item);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public Item getItemByTitle(String title) {
        return itemRepo.findItemByTitle(title);
    }

    public Item getItem(String id) {
        return itemRepo.findById(id).get();
    }

    public List<Item> getItemsFromCategory(String category){
        return itemRepo.findAllInCategory(category);
    }

    public List<Item> fetchAllItems(){
        return this.itemRepo.findAll();
    }
}

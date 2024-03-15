package com.example.wardrobe.service;

import com.example.wardrobe.model.Image;
import com.example.wardrobe.model.Item;
import com.example.wardrobe.repository.ItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ItemService {
    @Autowired
    private final ItemRepository itemRepo;

    public ItemService(ItemRepository itemRepo) {
        this.itemRepo = itemRepo;
    }

    public String createItem(String title, String size, String brand, String category) {
        if (itemRepo.findItemByTitle(title) != null) {
            throw new IllegalArgumentException("An item with the same title already exists");
        }

        Item item = new Item(title);
        item.setId(UUID.randomUUID().toString());
        item.setCategory(category);
        item.setSize(size);
        item.setBrand(brand);
        item = itemRepo.insert(item);

        return item.getId();
    }

    public Item addImageToItem(Image image, Item item) throws IllegalArgumentException, IllegalStateException {
        if (item == null || image == null) {
            throw new IllegalArgumentException("Item or image cannot be null.");
        }

        try {
            List<Image> images = item.getImages();
            images.add(image);
            item.setImages(images);

            Integer updateCount = itemRepo.updateItem(item.getId(), item);
            if (updateCount == null || updateCount == 0) {
                throw new IllegalStateException("Failed to update the item with the new image.");
            }

            return item;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to add the image to item: " + e.getMessage());
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

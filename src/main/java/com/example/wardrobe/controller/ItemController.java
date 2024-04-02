package com.example.wardrobe.controller;

import com.example.wardrobe.model.Image;
import com.example.wardrobe.model.Item;
import com.example.wardrobe.service.ImageService;
import com.example.wardrobe.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

    @GetMapping("/items/all")
    public ResponseEntity<List<Item>> fetchAllItems() {
        return ResponseEntity.ok(itemService.fetchAllItems());
    }

    @PostMapping("/items/createItem")
    public ResponseEntity<String> createItem(@RequestParam("title") String title, @RequestParam("brand") String brand,
                                             @RequestParam("size") String size,
                                             @RequestParam("category") String category, @RequestParam("images") List<MultipartFile> images)
            throws IOException {
        try {
            String itemId = itemService.createItem(title, size, brand, category);
            Item item = itemService.getItem(itemId);

            for (MultipartFile file : images) {
                String imageId = imageService.addImage(title, file);
                Image image = imageService.getImage(imageId);

                item = itemService.addImageToItem(image, item);
                if (item == null) {
                    return ResponseEntity.internalServerError().body("There was an issue adding the image to the item.");
                }
            }

            return ResponseEntity.ok().body(itemId);
        } catch (Exception e) {
            // delete the item?
            return ResponseEntity.internalServerError().body("An error occurred during the creation of the item: " + e);
        }
    }

    @GetMapping("/items/getItem")
    public ResponseEntity<Item> getItemByTitle(@RequestParam("title") String title){
        Item item = itemService.getItemByTitle(title);

        if (item != null) {
            return ResponseEntity.ok().body(item);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/items/getItemsFromCategory")
    public ResponseEntity<List<Item>> getItemsFromCategory(@RequestParam("category") String category){
        List<Item> items = itemService.getItemsFromCategory(category);

        if (items != null) {
            return ResponseEntity.ok().body(items);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable String id) {
        Item itemToDelete = itemService.getItem(id);
        if (itemToDelete != null){
            itemService.delete(itemToDelete);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}







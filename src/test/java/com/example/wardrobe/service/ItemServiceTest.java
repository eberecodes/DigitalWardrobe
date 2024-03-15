package com.example.wardrobe.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.example.wardrobe.model.Image;
import com.example.wardrobe.model.Item;
import com.example.wardrobe.repository.ItemRepository;
import org.bson.types.Binary;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class ItemServiceTest {

    private ItemRepository itemRepo;
    private ItemService itemService;

    @Before
    public void setUp() {
        itemRepo = mock(ItemRepository.class);
        itemService = new ItemService(itemRepo);
    }

    @Test
    public void testCreateItem() {
        String title = "title";
        String size = "small";
        String brand = "brand";
        String category = "category";
        String expectedId = UUID.randomUUID().toString();

        Item itemToInsert = new Item(title);
        itemToInsert.setId(expectedId);
        itemToInsert.setCategory(category);
        itemToInsert.setSize(size);
        itemToInsert.setBrand(brand);
        when(itemRepo.insert(any(Item.class))).thenReturn(itemToInsert);

        String actualId = itemService.createItem(title, size, brand, category);

        assertEquals(expectedId, actualId);
        verify(itemRepo).insert(any(Item.class));
    }

    @Test
    public void testCreateItemWithATitleThatExistsAlready() {
        String title = "title";
        String size = "small";
        String brand = "brand";
        String category = "category";

        Item itemInRepo = new Item(title);
        when(itemRepo.findItemByTitle(title)).thenReturn(itemInRepo);

        assertThrows(IllegalArgumentException.class, () -> itemService.createItem(title, size, brand, category));
        verify(itemRepo).findItemByTitle(title);
    }

    @Test
    public void testAddImageToItem() {
        String title = "test title";
        String id = UUID.randomUUID().toString();
        Image image = new Image(title);
        byte[] data = new byte[] {56, 99, 87, 77, 73, 90, 105, -23, -52, -85, -9, -55, -115, 11, -127, -127};
        image.setImage(new Binary(data));

        Item item = new Item(title);
        item.setId(id);

        when(itemRepo.updateItem(eq(id), any(Item.class))).thenReturn(1);

        Item updatedItem = itemService.addImageToItem(image, item);

        assertNotNull(updatedItem.getImages());
        assertEquals(image, updatedItem.getImages().get(0));

        verify(itemRepo).updateItem(eq(id), eq(item));
    }

    @Test
    public void testAddImageToItemWhenImageIsNull() {
        String title = "test title";
        Item item = new Item(title);

        assertThrows(IllegalArgumentException.class, () -> itemService.addImageToItem(null, item));
    }

    @Test
    public void testAddImageToItemWhenItemIsNull() {
        String title = "test title";
        Image image = new Image(title);
        byte[] data = new byte[] {56, 99, 87, 77, 73, 90, 105, -23, -52, -85, -9, -55, -115, 11, -127, -127};
        image.setImage(new Binary(data));

        assertThrows(IllegalArgumentException.class, () -> itemService.addImageToItem(image, null));
    }

    @Test
    public void testGetItemByTitle() {
        String title = "test title";
        Item expectedItem = new Item(title);
        when(itemRepo.findItemByTitle(title)).thenReturn(expectedItem);

        Item actualItem = itemService.getItemByTitle(title);

        assertEquals(expectedItem, actualItem);
        verify(itemRepo).findItemByTitle(title);
    }

    @Test
    public void testGetItemByTitleWhenTitleDoesNotExist() {
        String titleNotInRepo = "title";
        Item actualItem = itemService.getItemByTitle(titleNotInRepo);

        assertNull(actualItem);
        verify(itemRepo).findItemByTitle(titleNotInRepo);
    }

}
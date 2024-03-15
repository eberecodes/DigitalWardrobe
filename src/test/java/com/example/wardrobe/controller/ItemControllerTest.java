package com.example.wardrobe.controller;

import com.example.wardrobe.model.Image;
import com.example.wardrobe.model.Item;
import com.example.wardrobe.service.ImageService;
import com.example.wardrobe.service.ItemService;
import org.bson.types.Binary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@WebMvcTest(ItemController.class)
@AutoConfigureMockMvc
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @MockBean
    private ImageService imageService;

    @Autowired
    private ItemController itemController;


    @Test
    public void testCreateItem() throws IOException {
        String title = "some title";
        String brand = "some brand";
        String size = "some size";
        String category = "some category";
        String expectedId = "some id";
        String imageId = "some image id";

        byte[] data = new byte[] {56, 99, 87, 77, 73, 90, 105, -23, -52, -85, -9, -55, -115, 11, -127, -127};
        MultipartFile file = new MockMultipartFile("image", data);
        List<MultipartFile> images = Collections.singletonList(file);

        Image image = new Image(title);
        image.setImage(new Binary(data));

        Item expectedItem = new Item(title);
        expectedItem.setBrand(brand);
        expectedItem.setSize(size);
        expectedItem.setCategory(category);
        expectedItem.setImages(List.of(image));

        when(itemService.createItem(eq(title), eq(size), eq(brand), eq(category))).thenReturn(expectedId);
        when(itemService.getItem(eq(expectedId))).thenReturn(expectedItem);
        when(imageService.addImage(eq(title), eq(file))).thenReturn(imageId);
        when(imageService.getImage(eq(imageId))).thenReturn(image);
        when(itemService.addImageToItem(eq(image), eq(expectedItem))).thenReturn(expectedItem);

        ResponseEntity<String> responseEntity = itemController.createItem(title,brand, size, category,images);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(expectedId, responseEntity.getBody());

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(expectedId, responseEntity.getBody());
        verify(itemService, times(1)).createItem(eq(title), eq(size), eq(brand), eq(category));
        verify(itemService, times(1)).getItem(eq(expectedId));
        verify(imageService, times(1)).addImage(eq(title), eq(file));
        verify(imageService, times(1)).getImage(eq(imageId));
        verify(itemService, times(1)).addImageToItem(eq(image), eq(expectedItem));
    }

    @Test
    public void testCreateItemWhenIssueWithFile() throws IOException {
        String title = "some title";
        String brand = "some brand";
        String size = "some size";
        String category = "some category";
        String itemId = "some id";

        when(itemService.createItem(eq(title), eq(size), eq(brand), eq(category))).thenReturn(itemId);

        Item mockedItem = new Item(title);
        when(itemService.getItem(eq(itemId))).thenReturn(mockedItem);

        MultipartFile mockFile = new MockMultipartFile("test.jpg", new byte[]{});
        when(imageService.addImage(eq(title), eq(mockFile))).thenThrow(new IOException());

        ResponseEntity<String> responseEntity = itemController.createItem(title, brand, size, category, Collections.singletonList(mockFile));

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        Assertions.assertEquals("An error occurred during the creation of the item: "+ IOException.class.getName(), responseEntity.getBody());
    }

    @Test
    public void testGetItemByTitle() {
        String title = "test title";
        Item expectedItem = new Item(title);
        when(itemService.getItemByTitle(title)).thenReturn(expectedItem);

        ResponseEntity<Item> responseEntity = itemController.getItemByTitle(title);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(expectedItem, responseEntity.getBody());
    }

    @Test
    public void testGetItemByTitleWhenItemDoesNotExist() {
        String title = "non existent";
        when(itemService.getItemByTitle(title)).thenReturn(null);

        ResponseEntity<Item> responseEntity = itemController.getItemByTitle(title);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertNull(responseEntity.getBody());
    }
}

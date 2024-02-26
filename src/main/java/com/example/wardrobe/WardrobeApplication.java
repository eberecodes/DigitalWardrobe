package com.example.wardrobe;

import com.example.wardrobe.controller.ImageController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.wardrobe")
@EnableWebMvc
public class WardrobeApplication {

	public static void main(String[] args) {
		SpringApplication.run(WardrobeApplication.class, args);
	}

}

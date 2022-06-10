package com.project.inventoryservice;

import com.project.inventoryservice.model.Inventory;
import com.project.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository){

		return args -> {
			Inventory iphone = new Inventory();
			iphone.setSkuCode("iphone_13");
			iphone.setQuantity(100);

			Inventory samsung = new Inventory();
			samsung.setSkuCode("samsung_note_20");
			samsung.setQuantity(0);
			inventoryRepository.save(iphone);
			inventoryRepository.save(samsung);
		};
	}
}

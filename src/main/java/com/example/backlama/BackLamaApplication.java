package com.example.backlama;

import com.example.backlama.controllers.UserController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = UserController.class)
public class BackLamaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackLamaApplication.class, args);
	}

}

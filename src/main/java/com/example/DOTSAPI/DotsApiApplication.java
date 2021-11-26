package com.example.DOTSAPI;

import com.example.DOTSAPI.model.AppUser;
import com.example.DOTSAPI.model.Role;
import com.example.DOTSAPI.services.appUser.AppUserServices;
import com.example.DOTSAPI.services.product.ProductServices;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import java.util.HashSet;

@SpringBootApplication
public class DotsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DotsApiApplication.class, args);
	}

	@Bean
	public Argon2PasswordEncoder passwordEncoder() {
		return new Argon2PasswordEncoder();
	}

	@Bean
	CommandLineRunner run(AppUserServices appUserServices, ProductServices productServices) {
		return args -> {
			appUserServices.saveRole(new Role( "ROLE_USER"));
			appUserServices.saveRole(new Role( "ROLE_ADMIN"));
			appUserServices.saveRole(new Role("ROLE_SUPER_ADMIN"));

//			productServices.saveCategory(new Category("Sneaker"));
//			productServices.saveCategory(new Category("Sandal"));
			appUserServices.saveAppUser(new AppUser( "Tuan", "Nguyen", "tuanxsokoh", "tuanxsokoh@gmail.com",
					"123456", new HashSet<>()));
			appUserServices.saveAppUser(new AppUser("Long", "Nguyen", "longnguyen", "long@gmail.com",
					"123456", new HashSet<>()));

			appUserServices.addRoleToAppUser("tuanxsokoh", "ROLE_SUPER_ADMIN");
			appUserServices.addRoleToAppUser("tuanxsokoh", "ROLE_ADMIN");
		};
	}


}

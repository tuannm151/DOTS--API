package com.example.DOTSAPI;

import com.example.DOTSAPI.model.AppUser;
import com.example.DOTSAPI.model.Role;
import com.example.DOTSAPI.services.AppUserServices;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

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
	CommandLineRunner run(AppUserServices appUserServices) {
		return args -> {
			appUserServices.saveRole(new Role(null, "ROLE_USER"));
			appUserServices.saveRole(new Role(null, "ROLE_ADMIN"));
			appUserServices.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

			appUserServices.saveAppUser(new AppUser(null, "Tuan", "Nguyen", "tuanxsokoh", "tuanxsokoh@gmail.com",
					"123456", new ArrayList<>()));

			appUserServices.addRoleToAppUser("tuanxsokoh", "ROLE_SUPER_ADMIN");
			appUserServices.addRoleToAppUser("tuanxsokoh", "ROLE_ADMIN");
			appUserServices.addRoleToAppUser("tuanxsokoh", "ROLE_USER");
		};
	}


}

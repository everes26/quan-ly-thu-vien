package com.service.auth;

import com.service.auth.entitys.Role;
import com.service.auth.enums.RoleSystem;
import com.service.auth.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Configuration
@SpringBootApplication
public class ApiApplication {
	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Bean
	public void insertRole() {
		Arrays.asList(RoleSystem.ROLE_USER, RoleSystem.ROLE_ADMIN).forEach(roleSystem -> {
			if (!roleRepository.existsByName(roleSystem)) {
				roleRepository.save(new Role(roleSystem));
			}
		});
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}

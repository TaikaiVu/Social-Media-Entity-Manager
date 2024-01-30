package ca.gbc.userentity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "ca.gbc.userentity.model")

public class UserEntityApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserEntityApplication.class, args);
	}

}


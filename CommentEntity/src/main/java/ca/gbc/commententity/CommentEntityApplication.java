package ca.gbc.commententity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = {"ca.gbc.userentity", "ca.gbc.commententity"})

public class CommentEntityApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommentEntityApplication.class, args);
    }

}

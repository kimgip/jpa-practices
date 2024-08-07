package ex03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"ex03.domain"})
public class Ex03Application {
    public static void main(String[] args) {
        SpringApplication.run(Ex03Application.class, args);
    }
}
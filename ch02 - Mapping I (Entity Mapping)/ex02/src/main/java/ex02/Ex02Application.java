package ex02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"ex02.domain"})
public class Ex02Application {
    public static void main(String[] args) {
        SpringApplication.run(Ex02Application.class, args);
    }
}
package ex02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.test.context.ActiveProfiles;

@SpringBootApplication
public class Ex02Application {
    public static void main(String[] args) {
        SpringApplication.run(Ex02Application.class, args);
    }

    @ActiveProfiles({"test01", "test02", "test03", "test04", "test05"})
    @SpringBootConfiguration
    @EntityScan(basePackages = {"ex02.domain01_05"})
    public class ConfigDomain01_05 {
    }

    @ActiveProfiles({"test06", "test07"})
    @SpringBootConfiguration
    @EntityScan(basePackages = {"ex02.domain06_07"})
    public class onfigDomain06 {
    }
}
package org.psawesome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:db.properties")
public class ConvertingByReactiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConvertingByReactiveApplication.class, args);
    }

}

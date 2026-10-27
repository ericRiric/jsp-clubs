package com.example.clubs;

import com.example.clubs.Security.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({RsaKeyProperties.class})
@SpringBootApplication
public class ClubsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClubsApplication.class, args);
    }

}

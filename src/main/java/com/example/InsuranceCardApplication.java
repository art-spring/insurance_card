package com.example;

import com.example.card.wechat.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({Config.class})
public class InsuranceCardApplication {
	public static void main(String[] args) {
		SpringApplication.run(InsuranceCardApplication.class, args);
	}
}

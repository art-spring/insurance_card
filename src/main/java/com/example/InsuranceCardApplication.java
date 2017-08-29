package com.example;

import com.example.card.config.SmsConfig;
import com.example.card.wechat.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({Config.class, SmsConfig.class})
public class InsuranceCardApplication {
	public static void main(String[] args) {
		SpringApplication.run(InsuranceCardApplication.class, args);
	}
}

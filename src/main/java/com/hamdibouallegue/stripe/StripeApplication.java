package com.hamdibouallegue.stripe;

import com.hamdibouallegue.stripe.settings.StripeSettings;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StripeSettings.class)
public class StripeApplication {

	public static void main(String[] args) {
		SpringApplication.run(StripeApplication.class, args);
	}

}

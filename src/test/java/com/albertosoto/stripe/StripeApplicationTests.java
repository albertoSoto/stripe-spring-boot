package com.albertosoto.stripe;

import com.albertosoto.stripe.settings.StripeSettings;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
@EnableConfigurationProperties(StripeSettings.class)
class StripeApplicationTests {

	@Autowired
	StripeSettings stripeSettings;

	@Test
	void contextLoads() {
		Assert.isTrue(StringUtils.equals(stripeSettings.getUsername(),"foo"),"Settings not loaded");
	}

}

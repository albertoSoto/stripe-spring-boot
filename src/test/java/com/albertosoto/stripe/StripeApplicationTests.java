package com.albertosoto.stripe;

import com.albertosoto.stripe.service.PaymentService;
import com.albertosoto.stripe.settings.StripeSettings;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.PriceCollection;
import com.stripe.model.checkout.Session;
import com.stripe.param.PriceListParams;
import com.stripe.param.checkout.SessionCreateParams;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.net.URI;

@SpringBootTest
@EnableConfigurationProperties(StripeSettings.class)
class StripeApplicationTests {

	@Autowired
	StripeSettings stripeSettings;

	@Autowired
	PaymentService paymentService;

	@Test
	void contextLoads() {
		Assert.isTrue(StringUtils.equals(stripeSettings.getUsername(),"foo"),"Settings not loaded");
		Assert.isTrue(!StringUtils.equals(stripeSettings.getApiKey(),"<change-me>"),"API key not configured");
	}


	@Test
	void retrieveStripeSubscriptionProduct(){
		try{
			Stripe.apiKey = stripeSettings.getApiKey();
			PriceCollection prices  = paymentService.getPricesByLookupKey("subscription");
			Price price =prices.getData().get(0);
			Session session = paymentService.generateSession(price);
			//response.redirect(session.getUrl(), 303);
			URI yahoo = new URI(session.getUrl());
		}catch (Exception e){
			System.out.println("mek");
		}
	}
}

package com.albertosoto.stripe;

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

	@Test
	void contextLoads() {
		Assert.isTrue(StringUtils.equals(stripeSettings.getUsername(),"foo"),"Settings not loaded");
		Assert.isTrue(!StringUtils.equals(stripeSettings.getApiKey(),"<change-me>"),"API key not configured");
	}

	PriceCollection retrieveStripeActiveProducts() throws StripeException {
		Stripe.apiKey = stripeSettings.getApiKey();
		return Price.list(PriceListParams.builder().setActive(true).build());
		//https://stripe.com/docs/billing/prices-guide#lookup-keys
	}

	/**
	 *
	 * @return
	 * @throws StripeException
	 */
	PriceCollection retrieveStripeActiveProductsByLookUpKey() throws StripeException {
		String subscription = "subscription"; //defined in plan two > price > clave de busqueda
		Stripe.apiKey = stripeSettings.getApiKey();
		return Price.list(PriceListParams.builder().setActive(true).addLookupKeys(subscription).build());
		//https://stripe.com/docs/billing/prices-guide#lookup-keys
		//env var:https://docs.spring.io/spring-boot/docs/2.1.0.RELEASE/maven-plugin/examples/run-env-variables.html
	}

	@Test
	void retrieveStripeSubscriptionProduct(){
		String productId = "prod_KhNOMuLbFng2BB";
		String priceId = "price_1K1ydHKQQ8AMPSeP9IcEe4QW";
		try{
			Stripe.apiKey = stripeSettings.getApiKey();
			PriceCollection prices  = retrieveStripeActiveProductsByLookUpKey();
			SessionCreateParams params = SessionCreateParams.builder()
					.addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
					.addLineItem(
							SessionCreateParams.LineItem.builder().setPrice(prices.getData().get(0).getId()).setQuantity(1L).build())
					.setMode(SessionCreateParams.Mode.SUBSCRIPTION)
					.setSuccessUrl(stripeSettings.getCheckoutPage() + "?success=true&session_id={CHECKOUT_SESSION_ID}")
					.setCancelUrl(stripeSettings.getCheckoutPage() + "?canceled=true")
					.build();
			Session session = Session.create(params);
			//response.redirect(session.getUrl(), 303);
			URI yahoo = new URI(session.getUrl());
		}catch (Exception e){
			System.out.println("mek");
		}
	}
}

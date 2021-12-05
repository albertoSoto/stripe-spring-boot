package com.albertosoto.stripe.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.albertosoto.stripe.model.CheckoutPayment;
import com.albertosoto.stripe.settings.StripeSettings;
import com.stripe.model.Price;
import com.stripe.model.PriceCollection;
import com.stripe.param.PriceListParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.google.gson.Gson;
import com.albertosoto.stripe.model.Checkout;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@RestController
@RequestMapping(value = "/api")
public class StripeController {

	final
	StripeSettings stripeSettings;

	private static Gson gson = new Gson();

	public StripeController(StripeSettings stripeSettings) {
		this.stripeSettings = stripeSettings;
	}

	@GetMapping("/test")
	public String test(){
		Map<String, String> responseData = new HashMap<>();
		responseData.put("id", stripeSettings.getUsername());
		return gson.toJson(responseData);
	}


	@PostMapping("/create-checkout-session")
	public ResponseEntity<Object> checkout(@RequestParam(name = "lookup_key") String lookupKey){
		try {
			PriceListParams priceParams = PriceListParams.builder().addLookupKeys(lookupKey).build();
			PriceCollection prices  = Price.list(priceParams);
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
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setLocation(yahoo);
			return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
		} catch ( Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(StringUtils.EMPTY, HttpStatus.SEE_OTHER);
	}
	@PostMapping("/subscription")
	/**
	 * Used to create a subscription with strpe checkout page
	 *
	 * @param priceId
	 * @return the subscription id
	 * @throws StripeException
	 */
	public String subscriptionWithCheckoutPage(@RequestBody String priceId) throws StripeException {
		Checkout checkout = new Checkout ();
		checkout.setPriceId(priceId);
		checkout.setSuccessUrl(stripeSettings.getCheckoutPage() + "?success=true&session_id={CHECKOUT_SESSION_ID}");
		checkout.setCancelUrl(stripeSettings.getCheckoutPage() + "?canceled=true");
		return subscriptionWithCheckoutPage(checkout);
	}
	@PostMapping("/subscription-ori")
	/**
	 * Used to create a subscription with strpe checkout page
	 *
	 * @param checkout
	 * @return the subscription id
	 * @throws StripeException
	 */
	public String subscriptionWithCheckoutPage(@RequestBody Checkout checkout) throws StripeException {
		SessionCreateParams params = new SessionCreateParams.Builder().setSuccessUrl(checkout.getSuccessUrl())
				.setCancelUrl(checkout.getCancelUrl()).addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
				.setMode(SessionCreateParams.Mode.SUBSCRIPTION).addLineItem(new SessionCreateParams.LineItem.Builder()
						.setQuantity(1L).setPrice(checkout.getPriceId()).build())
				.build();
		try {
			Session session = Session.create(params);
			Map<String, Object> responseData = new HashMap<>();
			responseData.put("sessionId", session.getId());
			return gson.toJson(responseData);
		} catch (Exception e) {
			Map<String, Object> messageData = new HashMap<>();
			messageData.put("message", e.getMessage());
			Map<String, Object> responseData = new HashMap<>();
			responseData.put("error", messageData);
			return gson.toJson(responseData);
		}
	}
	@PostMapping("/payment")
	/**
	 * Payment with Stripe checkout page
	 * 
	 * @param payment
	 * @return
	 * @throws StripeException
	 */
	public String paymentWithCheckoutPage(@RequestBody CheckoutPayment payment) throws StripeException {
		// We initilize stripe object with the api key
		Stripe.apiKey = stripeSettings.getApiKey();
		// We create a stripe session
		SessionCreateParams params = SessionCreateParams.builder()
				// We will use the credit card payment method
				.addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
				.setMode(SessionCreateParams.Mode.PAYMENT).setSuccessUrl(payment.getSuccessUrl())
				.setCancelUrl(
						payment.getCancelUrl())
				.addLineItem(
						SessionCreateParams.LineItem.builder().setQuantity(payment.getQuantity())
								.setPriceData(
										SessionCreateParams.LineItem.PriceData.builder()
												.setCurrency(payment.getCurrency()).setUnitAmount(payment.getAmount())
												.setProductData(SessionCreateParams.LineItem.PriceData.ProductData
														.builder().setName(payment.getName()).build())
												.build())
								.build())
				.build();

		Session session = Session.create(params);

		Map<String, String> responseData = new HashMap<>();
		responseData.put("id", session.getId());

		return gson.toJson(responseData);
	}




}

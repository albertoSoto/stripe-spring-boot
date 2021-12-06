package com.albertosoto.stripe.service;

import com.albertosoto.stripe.settings.StripeSettings;
import com.stripe.Stripe;
import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import com.stripe.param.PriceListParams;
import com.stripe.param.checkout.SessionCreateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);
    final StripeSettings stripeSettings;

    public PaymentServiceImpl(StripeSettings stripeSettings) {
        this.stripeSettings = stripeSettings;
        Stripe.apiKey = stripeSettings.getApiKey();
    }

    /**
     * defined in plan two > price > clave de busqueda
     * //https://stripe.com/docs/billing/prices-guide#lookup-keys
     * //env var:https://docs.spring.io/spring-boot/docs/2.1.0.RELEASE/maven-plugin/examples/run-env-variables.html
     *
     * @param key
     * @return
     */
    @Override
    public PriceCollection getPricesByLookupKey(String key) {
        try {
            return Price.list(PriceListParams.builder().setActive(true).addLookupKeys(key).build());
        } catch (Exception e) {
            log.error("Searching prices", e);
            return null;
        }

    }

    @Override
    public List<com.albertosoto.stripe.model.Product> getProducts() {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("limit", 10);
            ProductCollection productCollection= Product.list(params);
            List<com.albertosoto.stripe.model.Product> products =new ArrayList<>();
            for (com.stripe.model.Product p: productCollection.getData()
            ) {
                com.albertosoto.stripe.model.Product aux= new com.albertosoto.stripe.model.Product();
                BeanUtils.copyProperties(p, aux);
                products.add(aux);
            }
            return products;
        } catch (Exception e) {
            log.error("Searching prices", e);
            return null;
        }
    }

    /**
     * Retrives all active products
     *
     * @return
     */
    @Override
    public PriceCollection getProductPrices() {
        try {
            return Price.list(PriceListParams.builder().setActive(true).build());
        } catch (Exception e) {
            log.error("Getting product prices", e);
            return null;
        }
    }

    @Override
    public Session generateSession(Price price) {
        try {
            if (price != null) {
                String priceId = price.getId();
                SessionCreateParams params = SessionCreateParams.builder()
                        .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                        .addLineItem(
                                SessionCreateParams.LineItem.builder().setPrice(priceId).setQuantity(1L).build())
                        .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                        .setSuccessUrl(stripeSettings.getCheckoutPage() + "?success=true&session_id={CHECKOUT_SESSION_ID}")
                        .setCancelUrl(stripeSettings.getCheckoutPage() + "?canceled=true")
                        .build();
                return Session.create(params);
            }
        } catch (Exception e) {
            log.error("generating Stripe session", e);
        }
        return null;
    }

    @Override
    public Session onCreatePortalSession() {
        try {

        } catch (Exception e) {
            log.error("MEK", e);
        }
        return null;
    }

    @Override
    public void onWebHookCall(String payload) {
        try {

        } catch (Exception e) {
            log.error("MEK", e);
        }
    }

    @Override
    public void onRecurringPaymentError(Subscription subscription) {
        try {
            log.info("recurring Payment Error");
        } catch (Exception e) {
            log.error("MEK", e);
        }
    }

    @Override
    public void onTrialEnd(Subscription subscription) {
        try {
            log.info("trial End");
        } catch (Exception e) {
            log.error("MEK", e);
        }
    }

    @Override
    public void onSubscriptionDelete(Subscription subscription) {
        try {
            log.info("subscription Delete");
        } catch (Exception e) {
            log.error("MEK", e);
        }

    }

    @Override
    public void onSubscriptionUpdated(Subscription subscription) {
        try {
            log.info("subscription Update");
        } catch (Exception e) {
            log.error("MEK", e);
        }
    }
}

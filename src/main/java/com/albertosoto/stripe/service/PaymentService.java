package com.albertosoto.stripe.service;

import com.stripe.model.*;
import com.stripe.model.checkout.Session;

import java.util.List;

public interface PaymentService {

    PriceCollection getPricesByLookupKey(String key);

    PriceCollection getProductPrices();

    Session generateSession(Price price);

    Session onCreatePortalSession();

    void onWebHookCall(String payload);

    void onRecurringPaymentError(Subscription subscription);

    void onTrialEnd(Subscription subscription);

    void onSubscriptionDelete(Subscription subscription);

    void onSubscriptionUpdated(Subscription subscription);

    List<com.albertosoto.stripe.model.Product> getProducts();

}

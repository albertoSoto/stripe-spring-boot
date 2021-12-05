package com.albertosoto.stripe.service;

import com.stripe.model.Price;
import com.stripe.model.PriceCollection;
import com.stripe.model.Product;
import com.stripe.model.Subscription;
import com.stripe.model.checkout.Session;

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

}

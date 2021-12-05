package com.albertosoto.stripe.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "stripe")
public class StripeSettings {
    String username;
    String apiKey;
    String checkoutPage;
    String endpointSecret;

    public String getEndpointSecret() {
        return endpointSecret;
    }

    public void setEndpointSecret(String endpointSecret) {
        this.endpointSecret = endpointSecret;
    }

    public String getCheckoutPage() {
        return checkoutPage;
    }

    public void setCheckoutPage(String checkoutPage) {
        this.checkoutPage = checkoutPage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}

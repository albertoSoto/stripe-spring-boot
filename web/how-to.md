# Stripe billing subscriptions, howto

Guia:
https://stripe.com/docs/billing/integration-builder?server=java
https://stripe.com/docs/billing/subscriptions/build-subscription

Guia a probar:
https://github.com/eugenp/tutorials/tree/master/stripe
https://www.baeldung.com/java-stripe-api
https://stackabuse.com/stripe-integration-with-java-spring-for-payment-processing/

https://stripe.com/docs/testing
https://hamdi-bouallegue.medium.com/online-payment-using-stripe-angular-and-spring-boot-bf576ad177b2

Steps:

1 - Generate products

- https://dashboard.stripe.com/products?active=true
  Una vez generado, copia el ID del producto After you create the prices, record the price IDs so you can use them in
  subsequent steps. Price IDs look like this: price_G0FvDp6vZvdwRZ.

1.1 - subscripcion
https://www.youtube.com/watch?v=92X_wj8opw0&list=PLyzY2l387AlPlX5gKQU9SRExGsu0NGW2X&index=17
Accede al enlace de subscripcion y copia el ID, es un id diferente al del producto

2 - Generate API keys
``
// Set your secret key. Remember to switch to your live secret key in production. // See your keys
here: https://dashboard.stripe.com/apikeys
On the backend of your application, define an endpoint that creates the session for your frontend to call. You need
these values:

- The price ID of the subscription the customer is signing up for—your frontend passes this value
- Your success_url, a page on your website that Checkout returns your customer to after they complete the payment
- Your cancel_url, a page on your website that Checkout returns your customer to if they cancel the payment process
  ``

## Stripe CLI install to test hooks under development

https://stripe.com/docs/stripe-cli

``
brew install stripe/stripe-cli/stripe stripe login stripe customers create brew upgrade stripe/stripe-cli/stripe
``

- Luego generar webhook para call backs

https://dashboard.stripe.com/webhooks/

Una vez autenticado, montamos un servidor de desarrollo para las callbacks
``
stripe listen --forward-to localhost:4242/webhook berto@MacBook-Pro-de-Alberto stripe-billing-sample-code % stripe listen --forward-to localhost:4242/webhook ⣟ Getting ready... > Ready! You are using Stripe API Version [2020-08-27]. Your webhook signing secret is whsec_LEwQRr5gBabz6Z59x6fe3Zbd4YKl7Uqe (^C to quit)
``

### MAC compatibility for dev

Using sdkman can create issues for GRAALVM 20x

```bash
$ sdk install java 21.2.0.r11-grl
$ sdk use java 21.2.0.r11-grl
#To return to previous version
$ sdk use java 20.3.4.r11-grl
```
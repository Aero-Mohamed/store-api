package com.hassan.store.services;

import com.hassan.store.entities.Order;
import com.hassan.store.exceptions.PaymentException;

import java.util.Optional;

public interface PaymentGateway {

    CheckoutSession createCheckoutSession(Order order) throws PaymentException;

    Optional<PaymentResult> parseWebhookRequest(WebhookRequest request);

}

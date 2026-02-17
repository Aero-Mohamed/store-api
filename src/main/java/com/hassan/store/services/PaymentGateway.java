package com.hassan.store.services;

import com.hassan.store.entities.Order;
import com.hassan.store.exceptions.PaymentException;

public interface PaymentGateway {

    CheckoutSession createCheckoutSession(Order order) throws PaymentException;

}

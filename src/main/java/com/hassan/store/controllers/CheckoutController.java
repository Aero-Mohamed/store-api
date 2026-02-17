package com.hassan.store.controllers;

import com.hassan.store.dtos.CheckoutRequest;
import com.hassan.store.dtos.CheckoutResponse;
import com.hassan.store.dtos.ErrorDto;
import com.hassan.store.exceptions.CartEmptyException;
import com.hassan.store.exceptions.CartNotFoundException;
import com.hassan.store.exceptions.PaymentException;
import com.hassan.store.services.CheckoutService;
import com.stripe.exception.StripeException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping
    public ResponseEntity<?> checkout(@Valid @RequestBody CheckoutRequest request){
        return ResponseEntity.ok(checkoutService.checkout(request));
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ErrorDto> handlePaymentException(PaymentException ex){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto("internal server error"));
    }

    @ExceptionHandler({CartNotFoundException.class, CartEmptyException.class})
    public ResponseEntity<ErrorDto> handleException(Exception ex){
        return ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));
    }
}

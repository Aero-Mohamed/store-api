# The Ultimate Spring Boot Course

This repository contains the starter project for Part 2 of my Spring Boot course:

[https://codewithmosh.com/p/spring-boot-building-apis](https://codewithmosh.com/p/spring-boot-building-apis)

## Stripe API Testing
- run `stripe listen --forward-to http://localhost:8080/checkout/webhook`
- then, copy webhook signing secret to `STRIPE_WEBHOOK_SECRET_KEY` 
- trigger event `stripe trigger payment_intent.succeeded --add "payment_intent:metadata[orderId]=3"`

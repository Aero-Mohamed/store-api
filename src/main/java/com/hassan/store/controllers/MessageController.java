package com.hassan.store.controllers;

import com.hassan.store.entities.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @GetMapping("/hello")
    public Message sayHello(){
        return new Message("Hello World!");
    }
}

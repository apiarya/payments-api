package com.islomar.payments_api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @RequestMapping("/hello-world")
    public String index() {
        return "Hello world";
    }
}
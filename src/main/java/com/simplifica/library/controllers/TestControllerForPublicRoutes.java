package com.simplifica.library.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tests")
public class TestControllerForPublicRoutes {
    @GetMapping("/hello/api")
    public  String helloApi() {
        return  "Hello API";
    }
}

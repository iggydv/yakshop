package com.xebia.yakshop.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<String> rootResponse() {
        String welcomeMessage = "Welcome to YakShop - Currently the site is still under construction. \nFeel free to explore our API at http://localhost:8080/swagger-ui";
        return new ResponseEntity<>(welcomeMessage, HttpStatus.OK);
    }
}

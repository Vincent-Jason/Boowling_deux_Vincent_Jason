package com.bowlinggenius.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);
    
    @GetMapping("/test")
    @ResponseBody
    public String testGet() {
        logger.info("Test GET reçu");
        return "Test GET réussi";
    }
    
    @PostMapping("/test-post")
    @ResponseBody
    public String testPost(@RequestParam("data") String data) {
        logger.info("Test POST reçu avec data: {}", data);
        return "Test POST réçu avec data: " + data;
    }
}

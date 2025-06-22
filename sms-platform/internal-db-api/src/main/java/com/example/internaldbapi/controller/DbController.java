package com.example.internaldbapi.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/db")
public class DbController {

    @GetMapping("/getAccountId")
    public Map<String, Object> getAccountId(@RequestParam String username, @RequestParam String password) {
        Map<String, Object> response = new HashMap<>();
        if ((username.equals("user1") && password.equals("password1"))) {
            response.put("account_id", 1001);
        } else if ((username.equals("user2") && password.equals("password2"))) {
            response.put("account_id", 1002);
        } else {
            response.put("error", "Invalid credentials");
        }
        return response;
    }

    @PostMapping("/insertSendMsg")
    public Map<String, Object> insertSendMsg(@RequestBody Map<String, Object> payload) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "inserted");
        response.put("data", payload);
        return response;
    }
}

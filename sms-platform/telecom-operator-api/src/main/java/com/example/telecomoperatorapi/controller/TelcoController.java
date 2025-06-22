package com.example.telecomoperatorapi.controller;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/telco")
public class TelcoController {

    @GetMapping("/smsc")
    public String sendToTelco(@RequestParam(required = false) Integer account_id,
                              @RequestParam(required = false) String mobile,
                              @RequestParam(required = false) String message) {
        if (account_id == null || mobile == null || message == null || mobile.length() != 10) {
            return "STATUS: REJECTED~~ RESPONSE_CODE: FAILURE";
        }
        String ackId = UUID.randomUUID().toString();
        return "STATUS: ACCEPTED~~RESPONSE_CODE: SUCCESS~~" + ackId;
    }
}

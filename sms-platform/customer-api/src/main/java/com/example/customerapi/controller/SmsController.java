package com.example.customerapi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/telco")
public class SmsController {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${internal.api.url}")
    private String internalDbApiUrl;

    @GetMapping("/sendmsg")
    public ResponseEntity<String> sendMessage(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String mobile,
            @RequestParam String message) {

        if (mobile == null || mobile.length() != 10 || message == null || message.isEmpty()) {
            return ResponseEntity.badRequest().body("STATUS: REJECTED~~ RESPONSE_CODE: FAILURE");
        }

        String validationUrl = internalDbApiUrl + "/db/getAccountId?username=" + username + "&password=" + password;
        ResponseEntity<Map> validationResponse = restTemplate.getForEntity(validationUrl, Map.class);

        if (!validationResponse.getStatusCode().is2xxSuccessful() || validationResponse.getBody().get("account_id") == null) {
            return ResponseEntity.badRequest().body("STATUS: REJECTED~~ RESPONSE_CODE: FAILURE");
        }

        int accountId = (int) validationResponse.getBody().get("account_id");
        String ackId = UUID.randomUUID().toString();

        Map<String, Object> payload = new HashMap<>();
        payload.put("account_id", accountId);
        payload.put("mobile", mobile);
        payload.put("message", message);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
        restTemplate.postForEntity(internalDbApiUrl + "/db/insertSendMsg", request, String.class);

        return ResponseEntity.ok("STATUS: ACCEPTED~~RESPONSE_CODE: SUCCESS~~" + ackId);
    }
}

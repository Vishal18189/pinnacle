package com.example.backendthreadapp.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SchedulerService {

    @Scheduled(fixedRate = 1000)
    public void pollSendMsgTable() {
        System.out.println("Polling DB for new messages...");
        // Simulate polling and sending to telecom operator
    }
}

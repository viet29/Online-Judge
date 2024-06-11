package com.service.notification_service.controller;

import com.service.notification_service.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/socket")
public class SocketController {
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public SocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/send")
    public ResponseEntity<Message> send(@RequestBody Message message) {
        messagingTemplate.convertAndSend("/topic/notification", message);
        System.out.println(message);
        return ResponseEntity.ok(message);
    }
}

package com.vprofile.app.controller;

import com.vprofile.app.model.EventRequest;
import com.vprofile.app.service.S3EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventController {

    private final S3EventService s3EventService;

    public EventController(S3EventService s3EventService) {
        this.s3EventService = s3EventService;
    }

    @PostMapping
    public ResponseEntity<String> ingestEvent(@RequestBody EventRequest event) {
        try {
            s3EventService.saveEvent(event);
            return ResponseEntity.ok("Event stored successfully in S3");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to store event");
        }
    }
}


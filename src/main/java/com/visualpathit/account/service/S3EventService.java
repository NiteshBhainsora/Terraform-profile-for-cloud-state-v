package com.vprofile.app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vprofile.app.model.EventRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class S3EventService {

    private final S3Client s3Client;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${app.s3.bucket}")
    private String bucketName;

    public S3EventService() {
        this.s3Client = S3Client.builder()
                .region(Region.US_EAST_1) // change if your bucket is in another region
                .build();
    }

    public void saveEvent(EventRequest event) throws Exception {

        String fileName = "raw-events/event-" + System.currentTimeMillis() + ".json";

        String content = objectMapper.writeValueAsString(event);

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType("application/json")
                .build();

        s3Client.putObject(request, RequestBody.fromString(content));
    }
}


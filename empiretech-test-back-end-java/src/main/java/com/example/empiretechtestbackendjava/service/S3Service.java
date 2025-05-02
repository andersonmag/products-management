package com.example.empiretechtestbackendjava.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class S3Service {

    @Value(value = "${aws.s3.bucket-name}")
    private String bucketName;
    private final AmazonS3Client s3Client;

    public S3Service(AmazonS3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(String path, File fileConverted) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentDisposition("inline");

        String contentType = Files.probeContentType(fileConverted.toPath());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        metadata.setContentType(contentType);

        s3Client.putObject(new PutObjectRequest(bucketName, path, fileConverted).withMetadata(metadata));
        Files.delete(fileConverted.toPath());

        return s3Client.getResourceUrl(bucketName, path);
    }
}

package com.example.empiretechtestbackendjava.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class S3Service {

    @Value(value = "${aws.s3.bucket-name}")
    private String bucketName;
    private final AmazonS3Client s3Client;

    public S3Service(AmazonS3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(String path, MultipartFile file) throws IOException {
        var fileConverted = convertMultiPartToFile(file);
        s3Client.putObject(new PutObjectRequest(bucketName, path, fileConverted));
        fileConverted.delete();

        return s3Client.getResourceUrl(bucketName, path);
    }

    private File convertMultiPartToFile(MultipartFile multipartFile) throws IOException {
        var file = new File(multipartFile.getOriginalFilename() );
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.close();
        return file;
    }
}

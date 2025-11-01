package ru.ivanov.ecommerceplatformproject.productservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {
    @Value("${yandex.cloud.s3.bucket}")
    private String bucketName;

    private final S3Client s3Client;

    public String uploadImage(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();

            PutObjectResponse response = s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileName)
                            .contentType(file.getContentType())
                            .acl("public-read")
                            .build(),
                    RequestBody.fromBytes(file.getBytes())
            );

            return String.format("https://storage.yandexcloud.net/%s/%s",
                    bucketName,
                    fileName);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to Yandex Object Storage", e);
        }
    }

   public void deleteImage(String path) {

   }
}
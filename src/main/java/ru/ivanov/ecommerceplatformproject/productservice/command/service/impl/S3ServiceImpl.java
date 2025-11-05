package ru.ivanov.ecommerceplatformproject.productservice.command.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.ivanov.ecommerceplatformproject.productservice.command.service.S3Service;
import ru.ivanov.ecommerceplatformproject.productservice.util.FileValidator;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    private static final String IMAGE_FOLDER = "product-images";

    @Value("${yandex.cloud.s3.bucket}")
    private String bucketName;

    private final FileValidator fileValidator;
    private final S3Client s3Client;

    public String uploadFile(MultipartFile file) {
        fileValidator.validateFile(file); //todo хотя это на фронте лучше сделать

        String key = IMAGE_FOLDER + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();

        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
//                    .acl("public-read")
                    .build();

            s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));

        } catch (S3Exception | IOException e) {
            throw new RuntimeException("Failed to upload file to S3", e); //DoCustomException
        }

        return String.format("https://storage.yandexcloud.net/%s/%s", bucketName, key);
    }

    @Override
   public void deleteFile(String fileUrl) {
        String key = extractKeyFromUrl(fileUrl);
        s3Client.deleteObject(builder -> builder
                .bucket(bucketName)
                .key(key)
        );
   }

   private String extractKeyFromUrl(String url) {
       return url.replaceFirst("https://[^/]+/", "");
   }
}
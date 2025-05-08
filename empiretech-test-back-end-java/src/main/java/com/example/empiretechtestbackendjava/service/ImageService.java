package com.example.empiretechtestbackendjava.service;

import com.example.empiretechtestbackendjava.domain.entities.ImageProduct;
import com.example.empiretechtestbackendjava.domain.entities.Product;
import com.example.empiretechtestbackendjava.exception.ImageSaveException;
import com.example.empiretechtestbackendjava.repository.ImageProductRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ImageService {

    private final ImageProductRepository imageRepository;
    private final S3Service s3Service;

    public void saveImages(List<MultipartFile> images, Product productSaved) {
        for (MultipartFile file : images) {
            final String bucketSaveImages = "products";
            var imageName = UUID.randomUUID() + "_" + productSaved.getId();
            var completePath = String.format("%s%s%s", bucketSaveImages, File.separator, imageName);

            try {
                final String savedPath = s3Service.uploadFile(completePath, convertMultiPartToFile(file));

                ImageProduct image = new ImageProduct(productSaved, imageName, savedPath, file.getContentType());
                imageRepository.save(image);
                productSaved.getImages().add(image);
            } catch (IOException e) {
                throw new ImageSaveException("Error saving exception", e);
            }
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        final String fileExtension = getFileExtension(file);
        final File tempFile = File.createTempFile("upload", "." + fileExtension);

        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(file.getBytes());
        }
        return tempFile;
    }

    private String getFileExtension(MultipartFile file) {
        String fileExtension = "tmp";
        final String fileName = file.getOriginalFilename();

        if (!StringUtils.isBlank(fileName) && fileName.contains(".")) {
            fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1);
        }
        return fileExtension;
    }

    @Transactional(readOnly = true)
    public ImageProduct getProductByIdAndProductId(UUID imageId, Long productId) {
        return imageRepository.findByIdAndProductId(imageId, productId)
                .orElseThrow(() -> new RuntimeException("Image not found or does not belong to the specified product"));
    }

    public void removeProductImage(ImageProduct productImage) {
        s3Service.removeS3File(productImage.getLink());
        imageRepository.deleteById(productImage.getId());
    }
}

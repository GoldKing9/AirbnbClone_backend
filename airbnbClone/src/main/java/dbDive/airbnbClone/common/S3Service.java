package dbDive.airbnbClone.common;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

// 위치
@Service
@RequiredArgsConstructor
public class S3Service {
    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public String uploadFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();

        try {
            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), new ObjectMetadata())
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to S3", e);
        }
    }

    public void deleteFile(String fileKey) {
        try {
            amazonS3.deleteObject(bucketName, fileKey);
        } catch (AmazonServiceException e) {
            throw new RuntimeException("Failed to delete file from S3", e);
        }
    }
}

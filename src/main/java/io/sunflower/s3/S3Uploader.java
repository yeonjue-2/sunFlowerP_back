package io.sunflower.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import io.sunflower.common.exception.ExceptionStatus;
import io.sunflower.common.exception.model.FileException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static io.sunflower.common.exception.ExceptionStatus.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;    // s3 버킷 이름

    /**
     * 이미지 1개 업로드
     */
    public String uploadFile(MultipartFile file, String dirName) {
        String fileName = dirName + "/" + createFileName(file.getOriginalFilename());

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        try(InputStream inputStream = file.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            return amazonS3Client.getUrl(bucket, fileName).toString();
        } catch (IOException e) {
            throw new FileException(IMAGE_UPLOAD_FAILED);
        }
    }

    /**
     * 이미지 여러개 업로드
     */
    public List<String> uploadFiles(List<MultipartFile> files, String dirName) {
        List<String> urls = new ArrayList<>();
        return getUrls(files, dirName, urls);
    }

    // 글 수정 시 기존 s3에 있는 이미지 정보 삭제
    public String deleteForReupload(MultipartFile file, String curFilePath, String imageKey) {
        String fileName = curFilePath + "/" + createFileName(file.getOriginalFilename());

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        amazonS3Client.deleteObject(bucket, imageKey);

        try (InputStream inputStream = file.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            return amazonS3Client.getUrl(bucket, fileName).toString();
        } catch (IOException e) {
            throw new FileException(IMAGE_UPLOAD_FAILED);
        }
    }

    // 글 수정 시 기존 s3에 있는 이미지 삭제 후 새로 저장
    public List<String> reupload(List<MultipartFile> files, String dirName, List<String> imageKeys) {
        List<String> urls = new ArrayList<>();

        for (String imageKey : imageKeys) {
            amazonS3Client.deleteObject(bucket, imageKey);
        }

        return getUrls(files, dirName, urls);
    }


    // =========================== 내부 메서드 =================================


    private String createFileName(String fileName) {
        // 파일명 난수화를 위해
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    /**
     * S3저장 후 url을 반환받는 메소드
     */
    private List<String> getUrls(List<MultipartFile> files, String dirName, List<String> urls) {
        for (MultipartFile file : files) {
            String fileName = dirName + "/" + createFileName(file.getOriginalFilename());

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try (InputStream inputStream = file.getInputStream()) {
                amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));

                urls.add(amazonS3Client.getUrl(bucket, fileName).toString());
            } catch (IOException e) {
                throw new FileException(IMAGE_UPLOAD_FAILED);
            }
        }
        return urls;
    }

    public void deleteImages(List<String> imageKeys) {
        for (String imageKey : imageKeys) {
            amazonS3Client.deleteObject(bucket, imageKey);
        }
    }

    private String getFileExtension(String fileName) {
        int index = fileName.lastIndexOf(".");
        return fileName.substring(index + 1);
    }

    /**
     * 단일 파일 확장자 검사
     */
    public void checkFileExtension(String fileName) {
        String substring = getFileExtension(fileName);

        boolean result = false;
        if (substring.equals("jpg") || substring.equals("jpeg") || substring.equals("png") || substring.equals("")) {
            result = true;
        }

        if (!result) {
            throw new FileException(UNSUPPORTED_IMAGE_TYPE);
        }
    }

    /**
     * 다중 파일 확장자 검사
     */
    public void checkFilesExtension(List<MultipartFile> files) { // 이 부분 이대로 사용할 것인지, 아니면 수정할 것인지 고민해보기
        List<String> extensions = new ArrayList<>();
        boolean result = true;

        files.forEach(file -> {
            String extension = getFileExtension(file.getOriginalFilename());
            extensions.add(extension);
        });

        for (String ext : extensions) {
            String extCase = ext.toLowerCase(Locale.ROOT);
            if (extCase.equals("jpg") || extCase.equals("jpeg") || extCase.equals("png") || extCase.equals("")) {
                result = true;
            } else {
                result = false;
            }
        }

        if (!result) {
            throw new FileException(UNSUPPORTED_IMAGE_TYPE);
        }
    }


    public void checkFileUpload(List<MultipartFile> files) {
        checkByFileCount(files); // 파일 갯수 확인
        checkFilesExtension(files); // 파일 확장자 검사
    }

    public void checkByFileCount(List<MultipartFile> files) {
        if (files.size() > 4) {
            throw new FileException(ExceptionStatus.TO_MUCH_FILES);
        }
    }

}

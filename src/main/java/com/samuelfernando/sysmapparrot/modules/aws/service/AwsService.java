package com.samuelfernando.sysmapparrot.modules.aws.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class AwsService {

	@Autowired
	private AmazonS3 amazonS3;

	@Value("${app-config.secrets.aws-s3-url}")
	private String s3Url;
	@Value("${app-config.secrets.aws-bucket-name}")
	private String bucketName;

	public String upload(MultipartFile multipartFile, String filename) throws Exception {
		String fileUri = "";

		try {
			File convertedFile = convertMultipartToFile(multipartFile);
			amazonS3.putObject(new PutObjectRequest(bucketName, filename, convertedFile)
					.withCannedAcl(CannedAccessControlList.PublicRead));
			
			fileUri = "/" + bucketName + "/" + filename;
			
			convertedFile.delete();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return fileUri;
	}

	private File convertMultipartToFile(MultipartFile file) throws IOException {
		File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));

		FileOutputStream fileOutputStream = new FileOutputStream(convertedFile);

		fileOutputStream.write(file.getBytes());
		fileOutputStream.close();

		return convertedFile;
	}
}

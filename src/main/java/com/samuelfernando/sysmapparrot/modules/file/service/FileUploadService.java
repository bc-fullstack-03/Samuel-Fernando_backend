package com.samuelfernando.sysmapparrot.modules.file.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.samuelfernando.sysmapparrot.modules.aws.service.AwsService;

@Service
public class FileUploadService implements IFileUploadService {
	
	@Autowired
	private AwsService awsService;

	public String upload(MultipartFile file, String filename) throws Exception {
		String fileUri = "";
		
		try {
			fileUri = awsService.upload(file, filename);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
		return fileUri;
	}
}

package com.samuelfernando.sysmapparrot.modules.file.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileUploadService {
	String upload(MultipartFile file, String filename) throws Exception;
}

package com.samuelfernando.sysmapparrot.config.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AwsConfig {
	@Value("${app-config.secrets.aws-access-key}")
	private String accessKey;
	@Value("${app-config.secrets.aws-secret-key}")
	private String secretKey;
	@Value("${app-config.secrets.aws-s3-url}")
	private String s3Url;
	
	@Bean
	public AmazonS3 amazonS3() {
		return AmazonS3ClientBuilder
				.standard()
				.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3Url, Regions.US_WEST_2.getName()))
				.withPathStyleAccessEnabled(true)
				.build();
	}
}

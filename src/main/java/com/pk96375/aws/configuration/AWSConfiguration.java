package com.pk96375.aws.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;

@Configuration
public class AWSConfiguration {

	@Value("${cloud.aws.credentials.access-key}")
	private String accesKey;
	@Value("${cloud.aws.credentials.secret-key}")
	private String secretKey;
	@Value("${cloud.aws.credentials.sqs.access-key}")
	private String sqsAccesKey;
	@Value("${cloud.aws.credentials.sqs.secret-key}")
	private String sqsSecretKey;

	public AmazonSNSClient snsClient() {
		return (AmazonSNSClient) AmazonSNSClientBuilder.standard().withRegion(Regions.US_EAST_1)
				.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accesKey, secretKey)))
				.build();
	}

	@Bean
	public QueueMessagingTemplate messagingTemplate() {
		return new QueueMessagingTemplate(buildAWSSQS());
	}

	private AmazonSQSAsync buildAWSSQS() {
		return AmazonSQSAsyncClientBuilder.standard().withRegion(Regions.US_EAST_1)
				.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(sqsAccesKey, sqsSecretKey)))
				.build();
	}
}

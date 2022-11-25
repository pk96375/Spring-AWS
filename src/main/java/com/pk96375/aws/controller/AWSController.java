package com.pk96375.aws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;

@RestController
public class AWSController {

	@Value("${cloud.aws.topic}")
	private String topic;
	@Value("${cloud.aws.sqs.end-point.url}")
	private String uri;
	@Autowired
	AmazonSNSClient amazonSNSClient;
	@Autowired
	QueueMessagingTemplate messagingTemplate;

	@GetMapping("/subscribe/{email}")
	public String subscribetoSNS(@PathVariable("email") String email) {
		SubscribeRequest subscribeRequest = new SubscribeRequest(topic, "email", email);

		amazonSNSClient.subscribe(subscribeRequest);
		return "Checj your Email";

	}

	@GetMapping("/publish/{msg}")
	public String publishtoSNS(@PathVariable("msg") String msg) {
		PublishRequest publishRequest = new PublishRequest(topic, msg, "SNS Spring Boot");
		amazonSNSClient.publish(publishRequest);

		return "Message published";

	}

	@GetMapping("put/{msg}")
	public void putMsg(@PathVariable("msg") String msg) {

		messagingTemplate.send(uri, MessageBuilder.withPayload(msg).build());
	}

	@SqsListener("sqs-queu")
	public void loadMsg(String msg) {
		System.out.println("Queue messages:" + msg);
	}
}

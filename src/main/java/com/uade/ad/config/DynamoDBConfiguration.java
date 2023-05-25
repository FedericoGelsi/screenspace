package com.uade.ad.config;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDBConfiguration {

/*    @Value("${amazon.region}")
    private String region;

    @Value("${dynamodb.endpoint.url}")
    private String dynamoDBEndpoint;*/

    // .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(dynamoDBEndpoint, region))

    @Bean
    public DynamoDBMapper dynamoDBMapper() {
        return new DynamoDBMapper(amazonDynamoDB());
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        return AmazonDynamoDBClientBuilder
                .standard()
                .withRegion(Regions.US_EAST_1)
                // .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(dynamoDBEndpoint, region))
                .build();
    }
}

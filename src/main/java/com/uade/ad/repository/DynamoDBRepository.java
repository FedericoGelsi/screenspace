package com.uade.ad.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.uade.ad.model.ScreenspaceTest;
import org.springframework.stereotype.Repository;

@Repository
public class DynamoDBRepository {

    private final DynamoDBMapper dynamoDBMapper;

    public DynamoDBRepository(final DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public String save(final ScreenspaceTest test) {
        try {
            dynamoDBMapper.save(test);
            return "Success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}

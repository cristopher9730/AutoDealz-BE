package com.autos.repository;

import com.autos.db.DataAccess;
import com.autos.domain.Car;
import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.CosmosDatabase;
import com.azure.cosmos.CosmosException;
import com.azure.cosmos.models.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;



public class CarRepository {
    private ObjectMapper objectMapper;
    private CosmosDatabase cosmosDatabase;
    private String DATABASE_ID;
    private String CONTAINER_ID;
    private CosmosClient cosmosClient = DataAccess
            .getCosmosClient();
    private CosmosContainer cosmosContainer;

    public List<Car> getAllCars() {

        List<Car> cars = new ArrayList<>();

        String sql = "SELECT * FROM cars";
        int maxItemCount = 1000;
        int maxDegreeOfParallelism = 1000;
        int maxBufferedItemCount = 100;

        CosmosQueryRequestOptions options = new CosmosQueryRequestOptions();
        options.setMaxBufferedItemCount(maxBufferedItemCount);
        options.setMaxDegreeOfParallelism(maxDegreeOfParallelism);
        options.setQueryMetricsEnabled(false);

        int error_count = 0;
        int error_limit = 10;

        String continuationToken = null;
        do {

            for (FeedResponse<JsonNode> pageResponse :
                    getContainerCreateResourcesIfNotExist()
                            .queryItems(sql, options, JsonNode.class)
                            .iterableByPage(continuationToken, maxItemCount)) {

                continuationToken = pageResponse.getContinuationToken();

                for (JsonNode item : pageResponse.getElements()) {

                    try {
                        cars.add(objectMapper.treeToValue(item, Car.class));
                    } catch (JsonProcessingException e) {
                        if (error_count < error_limit) {
                            error_count++;
                            if (error_count >= error_limit) {
                                System.out.println("\n...reached max error count.\n");
                            } else {
                                System.out.println("Error deserializing TODO item JsonNode. " +
                                        "This item will not be returned.");
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

        } while (continuationToken != null);
        return cars;
    }

    private CosmosContainer getContainerCreateResourcesIfNotExist() {

        try {

            if (cosmosDatabase == null) {
                CosmosDatabaseResponse cosmosDatabaseResponse = cosmosClient.createDatabaseIfNotExists(DATABASE_ID);
                cosmosDatabase = cosmosClient.getDatabase(cosmosDatabaseResponse.getProperties().getId());
            }

        } catch (CosmosException e) {
            // TODO: Something has gone terribly wrong - the app wasn't
            // able to query or create the collection.
            // Verify your connection, endpoint, and key.
            System.out.println("Something has gone terribly wrong - " +
                    "the app wasn't able to create the Database.\n");
            e.printStackTrace();
        }

        try {

            if (cosmosContainer == null) {
                CosmosContainerProperties properties = new CosmosContainerProperties(CONTAINER_ID, "/id");
                CosmosContainerResponse cosmosContainerResponse = cosmosDatabase.createContainerIfNotExists(properties);
                cosmosContainer = cosmosDatabase.getContainer(cosmosContainerResponse.getProperties().getId());
            }

        } catch (CosmosException e) {
            // TODO: Something has gone terribly wrong - the app wasn't
            // able to query or create the collection.
            // Verify your connection, endpoint, and key.
            System.out.println("Something has gone terribly wrong - " +
                    "the app wasn't able to create the Container.\n");
            e.printStackTrace();
        }

        return cosmosContainer;
    }

}

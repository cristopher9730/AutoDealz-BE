package com.autos.repository;

import com.autos.db.DataAccess;
import com.autos.domain.Car;
import com.azure.cosmos.CosmosException;
import com.azure.cosmos.models.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class CarRepository {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private DataAccess dataAccess;
    private final String DATABASE_ID = "Cars";
    private final String CONTAINER_ID = "1";
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
                    dataAccess.getCosmosClient().getDatabase(DATABASE_ID).getContainer(CONTAINER_ID)
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
                                System.out.println("Error deserializing Car item JsonNode. " +
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

    public void update(Car car) {
        try {
            dataAccess.getCosmosClient()
                    .getDatabase(DATABASE_ID)
                    .getContainer(CONTAINER_ID)
                    .replaceItem(car, car.getId(), new PartitionKey(car.getId()), new CosmosItemRequestOptions());
        } catch (CosmosException e) {
            System.out.println("Error updating TODO item.\n");
            e.printStackTrace();
        }
    }
}

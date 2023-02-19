package com.autos.db;

import com.autos.AppProperties;
import com.azure.cosmos.ConsistencyLevel;
import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;

public class DataAccess {
    private static CosmosClient cosmosClient = new CosmosClientBuilder()
            .endpoint(AppProperties.getInstance().getCosmosHost())
            .key(AppProperties.getInstance().getCosmosKey())
            .consistencyLevel(ConsistencyLevel.EVENTUAL)
            .buildClient();

    public static CosmosClient getCosmosClient() {
        return cosmosClient;
    }
}

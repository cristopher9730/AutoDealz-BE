package com.autos.db;
import com.autos.AppProperties;
import com.azure.cosmos.ConsistencyLevel;
import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@Component
public class DataAccess {
    @Autowired
    AppProperties appProperties;

    private CosmosClient cosmosClient;

    @PostConstruct
    private void init(){
     cosmosClient = new CosmosClientBuilder()
                .endpoint(appProperties.getHost())
                .key(appProperties.getKey())
                .consistencyLevel(ConsistencyLevel.EVENTUAL)
                .buildClient();
    }

    public CosmosClient getCosmosClient() {
        return cosmosClient;
    }
}

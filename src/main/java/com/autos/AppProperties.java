package com.autos;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "config")
public class AppProperties {
    private String host;
    private String key;
    private static AppProperties INSTANCE = null;
    private static AppProperties getInstance(){
        if(INSTANCE == null){
            INSTANCE = new AppProperties();
        }
        return INSTANCE;
    }
    public String getCosmosHost(){
        return host;
    }
}

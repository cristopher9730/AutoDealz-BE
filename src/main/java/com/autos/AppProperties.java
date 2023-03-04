package com.autos;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties
public class AppProperties {
    @Value("${cosmos.host}")
    private String cosmosHost;
    @Value("${cosmos.key}")
    private String cosmosKey;
    @Value("${cloudinary.upload.preset}")
    private String uploadPreset;
    @Value("${cloudinary.url}")
    private String cloudinaryUploadUrl;
}

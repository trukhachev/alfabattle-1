package com.trukhachev.twowayssl.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "app.alfabank")
public class AlfaBankClientProperties {

    private String url;

    private String clientId;
}

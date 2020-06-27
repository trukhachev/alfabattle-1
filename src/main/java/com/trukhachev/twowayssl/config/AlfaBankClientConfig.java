package com.trukhachev.twowayssl.config;

import com.trukhachev.twowayssl.client.alfabank.AlfaBankClient;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class AlfaBankClientConfig {

    private final AlfaBankClientProperties properties;

    @Bean
    public AlfaBankClient alfaBankClient() {
        return new AlfaBankClient(
                properties.getUrl(),
                properties.getClientId()
        );
    }

}

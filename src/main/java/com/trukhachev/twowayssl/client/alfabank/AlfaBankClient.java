package com.trukhachev.twowayssl.client.alfabank;

import com.trukhachev.twowayssl.client.alfabank.dto.AlfaBankResponseDTO;
import com.trukhachev.twowayssl.client.alfabank.dto.DataDTO;
import com.trukhachev.twowayssl.dto.ResponseDTO;
import io.netty.handler.ssl.SslContextBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.io.File;

@RequiredArgsConstructor
public class AlfaBankClient {

    private static final String METHOD_URI = "/atms";
    private final static int MAX_IN_MEMORY_SIZE = 16 * 1024 * 1024;

    private final WebClient webClient;

    public AlfaBankClient(final String url, final String clientId) {

        File privateKeyFile = null;
        File certFile = null;
        File trustFile = null;

        try {
            privateKeyFile = new File(System.getenv("API_PEM"));
            certFile = new File(System.getenv("API_CER"));
            trustFile = new File(System.getenv("API_TRUST"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        var sslContext = SslContextBuilder
                .forClient()
                .trustManager(trustFile)
                .keyManager(certFile, privateKeyFile, null);

        var client = HttpClient.create().secure(sslContextSpec -> {
            sslContextSpec.sslContext(sslContext);
        });

        webClient = WebClient.builder()
                .defaultHeaders(h -> {
                    h.setContentType(MediaType.APPLICATION_JSON);
                    h.set("x-ibm-client-id", clientId);
                })
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(client))
                .baseUrl(url)
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer
                                .defaultCodecs()
                                .maxInMemorySize(MAX_IN_MEMORY_SIZE))
                        .build())
                .build();
    }

    public AlfaBankResponseDTO getData() {

        return webClient
                .get()
                .uri(METHOD_URI)
                .retrieve()
                .bodyToMono(AlfaBankResponseDTO.class)
                .block();
    }
}

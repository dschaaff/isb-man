package net.javalib.isb.man.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HttpConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(clientHttpRequestFactory());
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() {
        HttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(150)
                .setMaxConnPerRoute(150)
                .build();
//        HttpClientConnectionManager poolingConnManager = new PoolingHttpClientConnectionManager();
//        CloseableHttpClient client = HttpClients.custom().setMaxConnTotal(1000).setsetConnectionManager(poolingConnManager).build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        factory.setReadTimeout(7000);
        factory.setConnectTimeout(7000);
        return factory;
    }

}

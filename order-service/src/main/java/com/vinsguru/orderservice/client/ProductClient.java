package com.vinsguru.orderservice.client;
import com.vinsguru.orderservice.dto.ProductDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class ProductClient {

    private final WebClient webClient;

    public ProductClient(@Value("${product.service.url}") String url){
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<ProductDto> getProductById(final String productId){
        return this.webClient
                .get()
                .uri("{id}", productId)
                .retrieve()
                .bodyToMono(ProductDto.class)
                .retry(3) // Intenta un máximo de 3 veces
                .timeout(Duration.ofSeconds(5))
                .doOnError(throwable -> {
                    //Aca coloco el logger
                }); // Tiempo de espera de 5 segundos antes de que se agote el tiempo de espera;
    }

    public Flux<ProductDto> getAllProducts(){
        return this.webClient
                .get()
                .uri("all")
                .retrieve()
                .bodyToFlux(ProductDto.class);
    }

}

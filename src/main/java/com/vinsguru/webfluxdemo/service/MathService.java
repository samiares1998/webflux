package com.vinsguru.webfluxdemo.service;

import com.vinsguru.webfluxdemo.dto.MultiplyRequestDTO;
import com.vinsguru.webfluxdemo.dto.Response;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MathService {
    public Mono<Response> findSquare(int input){
        return Mono.fromSupplier(()->input*input)
                .map(Response::new);
    }
    public Flux<Response> multiplicationTable(int input){
        return Flux.range(1,10)
                .map(i->new Response(i*input));
    }

    public Mono<Response> multiply(Mono<MultiplyRequestDTO> dtoMono){
        return dtoMono
                .map(dto -> dto.getFirst() * dto.getSecond())
                .map(Response::new);
    }
}

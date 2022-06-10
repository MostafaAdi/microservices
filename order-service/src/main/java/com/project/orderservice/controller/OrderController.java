package com.project.orderservice.controller;

import com.project.orderservice.dto.OrderRequest;
import com.project.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallBackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest) {

        //using completableFuture will make call placeOrder method in a separate thread
        //so if the method waits more than time limit the separate thread will throw an exception
        return CompletableFuture.supplyAsync(() -> orderService.placeOrder(orderRequest));
    }

    //return type must be same as original method(placeOrder)
    //when placeOrder fails it will throw a runtimeException, that is why we added RunTimeException in the parameter
    public CompletableFuture<String> fallBackMethod(OrderRequest orderRequest, RuntimeException runtimeException) {
        return CompletableFuture.supplyAsync(() -> "failed to place order, pleases try again later.");
    }



}

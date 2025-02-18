package com.rekreation.store.order.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface InventoryClient {

    Logger log = LoggerFactory.getLogger(InventoryClient.class);

    @GetExchange("/api/inventory")
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @Retry(name = "inventory")
    boolean isInStock(@RequestParam String skuCode, @RequestParam int quantity);

    default boolean fallbackMethod(String skuCode, int quantity, Throwable throwable) {
        log.info("Cannot get inventory status for skuCode: {} and quantity: {}. Failure reason: {}", skuCode, quantity, throwable.getMessage());
        return false;
    }
}

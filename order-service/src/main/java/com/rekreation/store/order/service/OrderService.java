package com.rekreation.store.order.service;

import com.rekreation.store.order.client.InventoryClient;
import com.rekreation.store.order.dto.OrderRequest;
import com.rekreation.store.order.event.OrderPlacedEvent;
import com.rekreation.store.order.model.Order;
import com.rekreation.store.order.repository.OrderRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private  final OrderRepository orderRepository;
  private final InventoryClient inventoryClient;
  private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

  public void placeOrder( OrderRequest orderRequest){

   var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

   if (isProductInStock){
     Order order = new Order();
     order.setOrderNumber(UUID.randomUUID().toString());
     order.setPrice(orderRequest.price());
     order.setSkuCode(orderRequest.skuCode());
     order.setQuantity(orderRequest.quantity());
     orderRepository.save(order);

     // send message to kafka topic for order processing
     OrderPlacedEvent orderPlacedEvent= new OrderPlacedEvent( order.getOrderNumber(), orderRequest.userDetails().email());
     log.info("start Sending Order Placed Event for Order Number: {}", order.getOrderNumber());
     kafkaTemplate.send("order_placed", orderPlacedEvent);
       log.info("end Seding Order Placed Event for Order Number: {}", order.getOrderNumber());
   }else{
      throw new RuntimeException("Product with SkuCode " +orderRequest.skuCode()+ "is out of stock");
   }


  }
}

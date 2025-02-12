package com.rekreation.store.order.service;

import com.rekreation.store.order.dto.OrderRequest;
import com.rekreation.store.order.model.Order;
import com.rekreation.store.order.repository.OrderRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

  private  final OrderRepository orderRepository;

  public void placeOrder( OrderRequest orderRequest){

        // map order request to order object
    Order order = new Order();
    order.setOrderNumber(UUID.randomUUID().toString());
    order.setPrice(orderRequest.price());
    order.setSkuCode(orderRequest.skuCode());
    order.setQuantity(orderRequest.quantity());

        // save order object to database
    orderRepository.save(order);

  }
}

package com.rekreation.store.order.service;

import com.rekreation.store.order.client.InventoryClient;
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
  private final InventoryClient inventoryClient;

  public void placeOrder( OrderRequest orderRequest){

   var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

   if (isProductInStock){
     Order order = new Order();
     order.setOrderNumber(UUID.randomUUID().toString());
     order.setPrice(orderRequest.price());
     order.setSkuCode(orderRequest.skuCode());
     order.setQuantity(orderRequest.quantity());
     orderRepository.save(order);
   }else{
      throw new RuntimeException("Product with SkuCode " +orderRequest.skuCode()+ "is out of stock");
   }


  }
}

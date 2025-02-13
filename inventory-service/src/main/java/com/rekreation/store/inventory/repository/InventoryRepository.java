package com.rekreation.store.inventory.repository;

import com.rekreation.store.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

 boolean existsBySkuCodeAndQuantityIsGreaterThanEqual(String skuCode, int quantity);
}

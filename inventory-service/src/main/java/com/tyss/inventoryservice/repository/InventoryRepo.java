package com.tyss.inventoryservice.repository;

import com.tyss.inventoryservice.model.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepo extends MongoRepository<Inventory, String> {

    List<Inventory> findBySkuCodeIn(List<String> skuCode);
}

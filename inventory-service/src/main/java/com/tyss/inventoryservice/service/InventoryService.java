package com.tyss.inventoryservice.service;

import com.tyss.inventoryservice.dto.InventoryResponse;
import com.tyss.inventoryservice.repository.InventoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryService {

    private InventoryRepo inventoryRepo;

    @Autowired
    public InventoryService(InventoryRepo inventoryRepo) {
        this.inventoryRepo = inventoryRepo;
    }

    public List<InventoryResponse> isInStock(List<String> skuCode) {
        return inventoryRepo.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                        InventoryResponse.builder()
                                .skucode(inventory.getSkuCode())
                                .isInStock(inventory.getQuantity() > 0)
                                .build()).toList();
    }
}

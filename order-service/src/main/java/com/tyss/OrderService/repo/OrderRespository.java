package com.tyss.OrderService.repo;

import com.tyss.OrderService.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRespository extends MongoRepository<Order, String> {
}

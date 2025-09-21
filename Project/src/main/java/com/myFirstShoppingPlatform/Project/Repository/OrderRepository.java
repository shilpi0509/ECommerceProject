package com.myFirstShoppingPlatform.Project.Repository;

import com.myFirstShoppingPlatform.Project.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    Order findByOrderId(int orderId);
}

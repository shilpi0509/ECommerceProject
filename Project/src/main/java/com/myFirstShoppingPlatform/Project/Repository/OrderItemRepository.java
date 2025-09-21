package com.myFirstShoppingPlatform.Project.Repository;

import com.myFirstShoppingPlatform.Project.Entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {
    OrderItem findByOrderId(int orderId );
}

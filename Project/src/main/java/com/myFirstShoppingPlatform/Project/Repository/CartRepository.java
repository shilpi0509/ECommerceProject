package com.myFirstShoppingPlatform.Project.Repository;


import com.myFirstShoppingPlatform.Project.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Integer> {
    Cart findByUserId(int userId);
}

package com.myFirstShoppingPlatform.Project.Repository;
import com.myFirstShoppingPlatform.Project.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long>{
    Product findBySerialNo(int serialNo);
}

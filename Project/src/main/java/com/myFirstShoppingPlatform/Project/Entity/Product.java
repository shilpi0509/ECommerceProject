package com.myFirstShoppingPlatform.Project.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @Column(name = "SerialNo")
    private int serialNo;

    @Column(name = "ProductName")
    private String productName;

    @Column(name = "Qty")
    private int qty;

    @Column(name = "Price")
    private double price;

}

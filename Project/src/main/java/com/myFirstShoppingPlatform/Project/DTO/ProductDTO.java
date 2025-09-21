package com.myFirstShoppingPlatform.Project.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private int serialNo;

    private String productName;

    private int qty;

    private double price;
}

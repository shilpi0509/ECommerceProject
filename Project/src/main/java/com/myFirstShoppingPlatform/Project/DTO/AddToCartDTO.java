package com.myFirstShoppingPlatform.Project.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddToCartDTO {
    private int userId;
    private int serialNo;
    private Double price;
}

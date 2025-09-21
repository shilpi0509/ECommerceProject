package com.myFirstShoppingPlatform.Project.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemoveFromCartDTO {

    private int userId;

    private int serialNo;

    private Double price;
}

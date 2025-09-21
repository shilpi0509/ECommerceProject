package com.myFirstShoppingPlatform.Project.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Cart {


    private Double cartValue;

    @Id
    private int userId;

    private String productList;
}

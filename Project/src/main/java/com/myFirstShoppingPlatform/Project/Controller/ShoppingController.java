package com.myFirstShoppingPlatform.Project.Controller;
import com.myFirstShoppingPlatform.Project.DTO.*;
import com.myFirstShoppingPlatform.Project.Entity.Product;
import com.myFirstShoppingPlatform.Project.Repository.ProductRepository;
import com.myFirstShoppingPlatform.Project.Response.ShoppingResponse;
import com.myFirstShoppingPlatform.Project.Service.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class ShoppingController {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ShoppingService shoppingServiceLayer;

@PostMapping(path = "/shopping/test")
public void AddProduct(@RequestBody List<ProductDTO> productDTOList){

    List<Product> productsList = new ArrayList<>();

    productDTOList.forEach(item->{
        productsList.add(Product.builder()
                        .serialNo(item.getSerialNo())
                        .productName(item.getProductName())
                        .qty(item.getQty())
                        .price(item.getPrice())
                .build());

    });

    productRepository.saveAll(productsList);

}

@PostMapping(path = "/shopping/addToCart")
    public ShoppingResponse AddProductToCart(@RequestBody AddToCartDTO addToCartDTO){

    return shoppingServiceLayer.addToCart(addToCartDTO);

}

@PostMapping(path="shopping/removeFromCart")
    public ShoppingResponse RemmoveProductFromCart(@RequestBody RemoveFromCartDTO removeFromCartDTO){

    return shoppingServiceLayer.removeFromCart(removeFromCartDTO);
}

@PostMapping(path= "shopping/orderPlace")
    public ShoppingResponse OrderPlace(@RequestBody PlaceOrderDTO placeOrderDTO){
    return shoppingServiceLayer.placeOrder(placeOrderDTO);
}

@PostMapping(path="shopping/cancelOrder")
    public ShoppingResponse CancelOrder(@RequestBody CancelOrderDTO cancelOrderDTO){

    return shoppingServiceLayer.cancelOrder(cancelOrderDTO);
}

@PostMapping(path="shopping/payments")
    public ShoppingResponse Payments(@RequestBody PaymentsDTO paymentsDTO){

    return shoppingServiceLayer.orderPayment(paymentsDTO);
}


}

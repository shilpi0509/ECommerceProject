package com.myFirstShoppingPlatform.Project.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myFirstShoppingPlatform.Project.DTO.*;
import com.myFirstShoppingPlatform.Project.Entity.*;
import com.myFirstShoppingPlatform.Project.Repository.CartRepository;
import com.myFirstShoppingPlatform.Project.Repository.OrderItemRepository;
import com.myFirstShoppingPlatform.Project.Repository.OrderRepository;
import com.myFirstShoppingPlatform.Project.Repository.ProductRepository;
import com.myFirstShoppingPlatform.Project.Response.ShoppingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ShoppingService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderItemRepository orderItemRepository;

    public ShoppingResponse addToCart(AddToCartDTO addToCartDTORequest){
        try {
            Product product = productRepository.findBySerialNo(addToCartDTORequest.getSerialNo());
            if (product != null) {
                Cart cart = cartRepository.findByUserId(addToCartDTORequest.getUserId());
                List<Product> productsInCart = new ArrayList<>();
                if (cart != null) {
                    productsInCart = objectMapper.readValue(
                            cart.getProductList(),
                            new TypeReference<List<Product>>() {
                            }
                    );
                    productsInCart.add(product);
                    cart.setProductList(objectMapper.writeValueAsString(productsInCart));

                    cart.setCartValue(cart.getCartValue()+product.getPrice());

                    cartRepository.save(cart);
                }else{
                    cart = Cart.builder()
                            .productList(objectMapper.writeValueAsString(List.of(product)))
                            .cartValue(product.getPrice())
                            .userId(addToCartDTORequest.getUserId())
                            .build();

                    cartRepository.save(cart);
                }
            }else{
                return ShoppingResponse.builder()
                        .statusMessage("Product Does Not Exist")
                        .build();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ShoppingResponse.builder()
                .statusMessage("Product Added To cart")
                .build();
    }

    public ShoppingResponse removeFromCart(RemoveFromCartDTO removeFromCartDTORequest){
        try {
            Cart cart = cartRepository.findByUserId(removeFromCartDTORequest.getUserId());

            if (cart != null) {
                Product product = productRepository.findBySerialNo(removeFromCartDTORequest.getSerialNo());
                List<Product>productsInCart = new ArrayList<>();
                if(product!=null){
                    productsInCart = objectMapper.readValue(
                            cart.getProductList(),
                            new TypeReference<List<Product>>() {}
                    );
                    productsInCart.remove(product);
                    cart.setProductList(objectMapper.writeValueAsString(productsInCart));

                    cart.setCartValue(cart.getCartValue()-product.getPrice());
                    cartRepository.save(cart);
                }
                else{
                    return ShoppingResponse.builder()
                            .statusMessage("Product does not exist, can not remove")
                            .build();
                }
            }else{
                return ShoppingResponse.builder()
                        .statusMessage("Cart does not exist, can not remove product")
                        .build();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ShoppingResponse.builder()
                .statusMessage("Product removed from Cart")
                .build();
    }

    public ShoppingResponse placeOrder(PlaceOrderDTO placeOrderDTO){
        try {
            Cart cart = cartRepository.findByUserId(placeOrderDTO.getUserId());

            if (cart != null) {
                Order order = Order.builder()
                        .orderId(generateOrderId())
                        .amount(cart.getCartValue())
                        .userId(cart.getUserId())
                        .orderStatus(OrderStatus.Placed)
                        .paymentStatus(PaymentStatus.Unpaid)
                        .build();

                orderRepository.save(order);

                List<OrderItem> orderItemList = new ArrayList<>();

                List<Product> productList = objectMapper.readValue(
                        cart.getProductList(),
                        new TypeReference<List<Product>>() {
                        }
                );

                productList.forEach(item->{
                    orderItemList.add(OrderItem.builder()
                                    .orderId(order.getOrderId())
                                    .price(item.getPrice())
                                    .qty(item.getQty())
                                    .serialNo(item.getSerialNo())
                            .build());
                });

                orderItemRepository.saveAll(orderItemList);
                cartRepository.delete(cart);

            } else {
                return ShoppingResponse.builder()
                        .statusMessage("Cart does not exist, item can not be removed")
                        .build();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ShoppingResponse.builder()
                .statusMessage("Order has been placed successfully")
                .build();
    }

    public ShoppingResponse cancelOrder(CancelOrderDTO cancelOrderDTO){
        Order order= orderRepository.findByOrderId(cancelOrderDTO.getOrderId());

        if(order!=null){
            if(order.getOrderStatus().equals(OrderStatus.Placed)){
                order.setOrderStatus(OrderStatus.Cancelled);
                orderRepository.save(order);
            }else{
                return ShoppingResponse.builder()
                        .statusMessage("Order has been already cancelled")
                        .build();
            }
        }else{
            return ShoppingResponse.builder()
                    .statusMessage("Order does not exist")
                    .build();
        }
        return ShoppingResponse.builder()
                .statusMessage("Order has been cancelled")
                .build();
    }

    public ShoppingResponse orderPayment(PaymentsDTO paymentsDTO){
        Order order =orderRepository.findByOrderId(paymentsDTO.getOrderId());

        if(order!=null){
            if(order.getPaymentStatus().equals(PaymentStatus.Unpaid) && !order.getOrderStatus().equals(OrderStatus.Cancelled)){
                order.setPaymentStatus(PaymentStatus.Paid);
                orderRepository.save(order);
            }else{
                return ShoppingResponse.builder()
                        .statusMessage("Order has been paid already")
                        .build();
            }
        }else{
            return ShoppingResponse.builder()
                    .statusMessage("Order does not exist")
                    .build();
        }

        return ShoppingResponse.builder()
                .statusMessage("Order has been paid successfully")
                .build();
    }


    public static int generateOrderId() {
        Random random = new Random();
        // Generate a positive 9-digit integer (to avoid overflow)
        return 100_000_000 + random.nextInt(900_000_000);
    }




}

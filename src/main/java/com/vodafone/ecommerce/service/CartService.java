package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final productService productService;

    public List<Product> addNewCartItem(List<Product> cartItems, Long id , Integer quantity){
        Product newCartItem = productService.getProductById(id);
        if(cartItems.stream().anyMatch(x->x.getName().equals(newCartItem.getName()))){
            Product product= cartItems.stream().filter(x->x.getName().equals(newCartItem.getName())).findFirst().get();
            product.setQuantity(product.getQuantity()+quantity);
            cartItems.removeIf(x->x.getName().equals(product.getName()));
            cartItems.add(product);
            return cartItems;
        }else{
            cartItems.add(Product.builder()
                            .id(id)
                    .name(newCartItem.getName())
                    .price(newCartItem.getPrice())
                    .image(newCartItem.getImage())
                    .quantity(quantity)
                    .build());
            return cartItems;
        }

    }
}

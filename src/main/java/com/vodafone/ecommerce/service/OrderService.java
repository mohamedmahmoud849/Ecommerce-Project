package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.model.Order;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.repo.ItemsQuantityProjection;
import com.vodafone.ecommerce.repo.OrderRepo;
import com.vodafone.ecommerce.repo.ProductRepo;
import com.vodafone.ecommerce.repo.Projection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.LongStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepo orderRepo;
    private final ProductService productService;
    private final RelationService relationService;
    private final ProductRepo productRepo;



    public List<Order> getAllOrders(){
        return orderRepo.findAll();
    }
    public Order insertNewOrder(List<Product> list){
        long totalQuantity=0L,totalPrice=0L;
        for (Product product:
                list) {
            totalQuantity += product.getQuantity();
            totalPrice += Long.valueOf(product.getPrice()) * product.getQuantity();
        }
        return orderRepo.save(Order.builder()
                .orderDate(new Date())
                .itemsQuantity(totalQuantity)
                .totalPrice(totalPrice)
                .build());
    }

    public void setOrderProductsRelation(List<Product> productsList){
        Order newOrder = insertNewOrder(productsList);
        relationService.createRelations(productsList,newOrder);
    }





    public Order getOrderDetails(Long id){
        return orderRepo.findById(id).get();
    }
    public List<Projection> getProjection(Long id){
        return orderRepo.getProjection(id);
    }

    public List<Product> getProductsForOrderDetails(List<Projection> list){
        return list.stream().flatMapToLong(x-> LongStream.of(x.getItemId())).mapToObj(productService::getProductById).toList();
    }
    public List<Product> getCardItemsForOrderDetails(List<Projection> list){
        List<Product> newCardItemsList = new ArrayList<>();
        List<Product> products = getProductsForOrderDetails(list);
        products.stream().map(Product::getImage).toList();
        for(int i=0;i< list.size();i++){
            newCardItemsList.add(Product.builder()
                    .name(products.get(i).getName())
                    .price(products.get(i).getPrice())
                    .image(products.get(i).getImage())
                    .quantity(Math.toIntExact(list.get(i).getQuantity()))
                    .build());
        }
        return newCardItemsList;
    }
    //TODO: to be handled -> break this function into two functions
    public boolean handleStock(List<Product> products) {
        List<ItemsQuantityProjection> itemsQuantity = productRepo.getAllProductsQuantity();
        for (Product product:
             products) {
            ItemsQuantityProjection itemQuantity = itemsQuantity.stream().filter(x-> x.getProductId() == product.getId()).findFirst().get();
            int stockProductsQuantity = itemQuantity.getProductQuantity();
            int orderProductQuantity = product.getQuantity();
            if (orderProductQuantity > stockProductsQuantity){
                return false;
            }else{
                int updatedStockProductQuantity = stockProductsQuantity - orderProductQuantity;
                updateProductQuantity(updatedStockProductQuantity,product.getId());
            }
        }
        return true;
    }
    public void updateProductQuantity(Integer quantity , Long id){
        productRepo.updateItemQuantityById(quantity,id);
    }


}

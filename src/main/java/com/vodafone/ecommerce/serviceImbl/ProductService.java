package com.vodafone.ecommerce.serviceImbl;

import com.vodafone.ecommerce.errorhandlling.NotFoundException;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.repo.ItemsQuantityProjection;
import com.vodafone.ecommerce.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;


    public Product getProductById(Long id) {
        return productRepo.findById(id).get();
    }

    public List<Product> getALlByCategory(String category) {
        return productRepo.findAllByCategory(category);
    }

    public List<Product> getALl(){
        return productRepo.findAll();
    }

    public void SaveImgDb(MultipartFile file, String name, Integer price, String category, Integer quantity) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            System.out.println("not a proper file name");
        } else {

            try {
                productRepo.save(Product.builder()
                        .image(Base64.getEncoder().encodeToString(file.getBytes()))
                        .name(name)
                        .quantity(quantity)
                        .price(price)
                        .category(category)
                        .build());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
    public void handleStock(List<Product> products) {
        List<ItemsQuantityProjection> itemsQuantity = productRepo.getAllProductsQuantity();
        for (Product product:
                products) {
            ItemsQuantityProjection itemQuantity = itemsQuantity.stream().filter(x-> x.getProductId() == product.getId()).findFirst().get();
            int stockProductsQuantity = itemQuantity.getProductQuantity();
            int orderProductQuantity = product.getQuantity();
            if (orderProductQuantity > stockProductsQuantity){
                throw new NotFoundException("no enough stock");
            }else{
                int updatedStockProductQuantity = stockProductsQuantity - orderProductQuantity;
                updateProductQuantity(updatedStockProductQuantity,product.getId());
            }
        }
    }
    public void updateProductQuantity(Integer quantity , Long id){
        productRepo.updateItemQuantityById(quantity,id);
    }


    public String calculateTotalPrice(List<Product> productsList) {
        Long totalPrice = 0L;
        if (productsList.isEmpty()) {
            return String.valueOf(totalPrice);

        } else {
            for (Product product :
                    productsList) {
                totalPrice += Long.valueOf(product.getPrice()) * product.getQuantity();
            }
            return String.valueOf(totalPrice);
        }


    }

    public Product getProductByName(String name) {
        return productRepo.findByName(name);
    }
}


package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.serviceImbl.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController extends BaseController {

    private final ProductService productService;

    @GetMapping("/add_product")
    public String showAddItemForm(){
        return "new_product";
    }
    @PostMapping("/add_product")
    public String addNewProduct(@RequestParam("file") MultipartFile file,
                                @RequestParam("name") String name,
                                @RequestParam("category") String category,
                                @RequestParam("price") Integer price,
                                @RequestParam("quantity") Integer quantity){
        productService.SaveImgDb(file,name, price, category,quantity);
        return "redirect:/";
    }
    @RequestMapping("/items/{id}")
    public String showItemPage(@PathVariable Long id, Model model){
        model.addAttribute("item",productService.getProductById(id));
        return "item_page";
    }
    @GetMapping("/items")
    @ResponseBody
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(productService.getALl());
    }



    @GetMapping("/test")
    public String showTestForm(){
        return "search_test";
    }
}


package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.service.productService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class productController {
    private final productService productService;
    @GetMapping("/")
    public String about(){
        return "new_product";
    }
    @PostMapping("/add_product")
    public String addNewProduct(@RequestParam("file") MultipartFile file,
                                @RequestParam("name") String name,
                                @RequestParam("category") String category,
                                @RequestParam("price") Integer price){
        productService.SaveImgDb(file,name, price, category);
        return "redirect:/";
    }
}

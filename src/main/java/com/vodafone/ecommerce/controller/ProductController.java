package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.model.State;

import com.vodafone.ecommerce.serviceImbl.ProductService;
import com.vodafone.ecommerce.serviceImbl.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController extends BaseController {

    private final ProductService productService;

    @GetMapping("/add_product")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showAddItemForm(){
        return "new_product";
    }
    @PostMapping("/add_product")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addNewProduct(@RequestParam("file") MultipartFile file,
                                @RequestParam("name") String name,
                                @RequestParam("category") String category,
                                @RequestParam("price") Integer price,
                                @RequestParam("quantity") Integer quantity){
        productService.SaveImgDb(file,name, price, category,quantity);
        return "redirect:/";
    }

//    @GetMapping("/update_product_list")
//    public String showEditProductList(Model model){
//        model.addAttribute("products", productService.getALl());
//        return "product_edit_list";
//    }

    @GetMapping("/update_product_list")
    public String showEditProductList(Model model){
        model.addAttribute("products", productService.getAllByArchived(Boolean.FALSE));
        return "product_edit_list";
    }

    @RequestMapping("/{id}")
    public String showItemPage(@PathVariable Long id, Model model){
        model.addAttribute("item",productService.getProductById(id));
        return "new_item_page";
    }
    @GetMapping("/items")
    @ResponseBody
    public List <Product> getAllProducts(){
        return productService.getALl();
    }



    @GetMapping("/test")
    public String showTestForm(){
        return "search_test";
    }

    @GetMapping("/update_product/{id}")
    public String updateProductDetails(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "update_product";}

    @PostMapping("/update_product/{id}")
    public String updateProductDetails(@PathVariable Long id,
                                     @RequestParam("name") String name,
                                     @RequestParam("price") Integer price,
                                       @RequestParam("quantity") Integer quantity,
                                     @RequestParam("category") String category) {
        productService.updateProduct(id, name, price, quantity, category);
        return "redirect:/products/update_product_list";}

    @GetMapping("/update_product_image/{id}")
    public String updateProductImage(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "update_product_image";}

    @PostMapping("/update_product_image/{id}")
    public String updateProductImage(@PathVariable Long id,
                                       @RequestParam("file") MultipartFile file) {
        productService.updateProductImage(id, file);
        return "redirect:/products/update_product_list";}

    @GetMapping("/delete_product/{id}")
    public String deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return "redirect:/products/update_product_list";}

    @GetMapping("/archive_product/{id}")
    public String archiveProduct(@PathVariable Long id){
        productService.archiveProduct(id);
        return "redirect:/products/update_product_list";}
}


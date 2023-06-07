package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.service.productService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
//@RequestMapping("api/v1/..")
public class productController {

    private final productService productService;

    @GetMapping("/add_product")
    public String addNewProduct(){
        return "new_product";
    }
    @PostMapping("/add_product")
    public String addNewProductP(@RequestParam("file") MultipartFile file,
                                @RequestParam("name") String name,
                                @RequestParam("category") String category,
                                @RequestParam("price") Integer price,
                                @RequestParam("quantity") Integer quantity){
        productService.SaveImgDb(file,name, price, category,quantity);
        return "redirect:/";
    }
    @RequestMapping("/items/{id}")
    public String itemPage(@PathVariable Integer id, Model model){
        model.addAttribute("item",productService.getProductById(id));
        /*httpSession.setAttribute("product", productService.getProductById(id));
        httpSession.setAttribute("ses_text", String.valueOf("lol"));
        model.addAttribute("session_obj",httpSession.getAttribute("product"));
        model.addAttribute("session_text",httpSession.getAttribute("ses_text"));*/
        return "item_page";
    }

}

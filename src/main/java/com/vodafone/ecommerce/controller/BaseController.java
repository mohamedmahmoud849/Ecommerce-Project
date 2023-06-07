package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.model.Product;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;


public class BaseController {
    @Autowired
    private HttpSession session;

    @ModelAttribute("session")
    public HttpSession getSession() {
        return session;
    }

}

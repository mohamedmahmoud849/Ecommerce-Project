package com.vodafone.ecommerce.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vodafone.ecommerce.service.AdminService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AdminControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    private String asJsonString(Object object) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

//    @Test
//    void addAdmin() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/add_admin")
//                        .param("email", "test@example.com")
//                        .param("username", "username")
//                        .param("password", "password"))
//                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
//                .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
//
//    }
//
//    @Test
//    public void testAddNewAdmin() throws Exception {
//        String email = "test@example.com";
//        String username = "testuser";
//        String password = "testpassword";
//
//        doNothing().when(adminService).addAdmin(email, username, password);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/add_admin")
//                        .param("email", email)
//                        .param("username", username)
//                        .param("password", password))
//                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
//                .andExpect(MockMvcResultMatchers.redirectedUrl("/edit_admins"));
//
//        verify(adminService, times(1)).addAdmin(email, username, password);
//    }

}
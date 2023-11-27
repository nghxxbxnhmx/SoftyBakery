package com.poly.restcontrollers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.dao.DiscountDAO;
import com.poly.dao.ProductDAO;
import com.poly.dto.ProductDTO;
import com.poly.models.Product;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/rest/discount")
public class DiscountRestController {
    @Autowired
    DiscountDAO dDAO;
    @Autowired
    ProductDAO pDAO;


    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping
    public String discount() throws JsonProcessingException {
        String temp = "";
        String [] productIdArray = dDAO.findById(1).get().getProductIdList().toString().split(" \\| ");
        List<Product> pDTOs = new ArrayList<>(); 
        for(String s : productIdArray) {
            pDTOs.add(pDAO.findById(Integer.parseInt(s)).get());
        }
        return objectMapper.writeValueAsString(pDTOs);
    }
}

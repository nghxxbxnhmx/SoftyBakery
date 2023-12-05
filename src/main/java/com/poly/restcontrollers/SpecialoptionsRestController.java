package com.poly.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.dao.SpecialOptionDAO;

import com.poly.models.SpecialOption;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/rest/Specialoption")
public class SpecialoptionsRestController {
   @Autowired
   SpecialOptionDAO sDao;
   
   @GetMapping
   public ResponseEntity<List<SpecialOption>> findAll() {
        return ResponseEntity.ok(sDao.findAll());
    }

    // @GetMapping("/{productId}")
    // public ResponseEntity<List<String>> findOptionsByProductId(@PathVariable("productId") Integer productId) {
    //     List<String> options = sDao.findOptionsByProductId(productId);
    //     return new ResponseEntity<>(options, HttpStatus.OK);
    // }
}

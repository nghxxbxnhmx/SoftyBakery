package com.poly.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PhotoReaderController {
    @GetMapping("/ReadPhoto/{photoAddress}")
    public String readPhoto(@PathVariable("photoAddress") String photoAddress) {
        
        return photoAddress;
    }
}

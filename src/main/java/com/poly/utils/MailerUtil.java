package com.poly.utils;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.services.CartService;

public class MailerUtil {
    @Autowired
    CartService cart;

    static ObjectMapper objectMapper = new ObjectMapper();

    public static void send() throws IOException {
        System.out.println("Send mail");
    };
}

package com.poly.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import org.springframework.stereotype.Component;

import com.nimbusds.jose.util.StandardCharset;

@Component
public class CurrencyUtil {
    public static String formatVnd(double amount) {
        NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        vndFormat.setCurrency(Currency.getInstance("VND"));
        return vndFormat.format(amount);
    }
}

package com.poly.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class JsonReaderUtil {
    private final static ObjectMapper objectMapper = new ObjectMapper();


    public static <T> List<T> read(String filePath, Class<T> valueType) {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(filePath));
            return objectMapper.readValue(jsonData, objectMapper.getTypeFactory().constructCollectionType(List.class, valueType));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> String write(T object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}

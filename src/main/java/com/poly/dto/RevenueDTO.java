package com.poly.dto;

import jakarta.persistence.Tuple;
import lombok.Data;

@Data
public class RevenueDTO {
    int year;
    int month;
    double totalRevenue;

    public RevenueDTO(Tuple tuple) {
        this.year = tuple.get("year", Integer.class);
        this.month = tuple.get("month", Integer.class);
        this.totalRevenue = tuple.get("totalRevenue", Double.class);
    }
}

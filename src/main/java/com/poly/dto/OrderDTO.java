package com.poly.dto;


import com.poly.models.Account;
import com.poly.models.Order;

import lombok.*;

@Data
public class OrderDTO extends Order{
    private Account account;
    public OrderDTO(Order order) {
    	super.setOrderId(order.getOrderId());
    	super.setOrderDate(order.getOrderDate());
    	super.setDiscountid(order.getDiscountid());
    	super.setAddress(order.getAddress());
    	super.setStatus(order.getStatus());
	}
}


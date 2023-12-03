package com.poly.dto.enums;
import lombok.Getter;
@Getter
public enum OrderStatusEnum {
    PENDING("Chờ xác nhận", "Đơn hàng đang chờ xác nhận"),
    CONFIRMED("Đã xác nhận", "Đơn hàng đã được xác nhận"),
    OUT_FOR_DELIVERY("Đang giao hàng", "Đơn hàng đang trong quá trình giao hàng"),
    SHIPPING("moe meo","moe meo"),
    DELIVERED("Đã giao hàng", "Đơn hàng đã được giao thành công"),
    CANCELED("Đã hủy", "Đơn hàng đã bị hủy"),
    PROCESSING("Đang xử lý", "Đơn hàng đang trong quá trình xử lý"),
    REFUNDED("Đã hoàn tiền", "Đã hoàn tiền cho đơn hàng"),
    DELIVERING("Đang xử lý", "Đơn hàng đang trong quá trình xử lý"),
    ERROR("Lỗi", "Đã xảy ra lỗi trong quá trình xử lý đơn hàng");
    
    private final String description;
    private final String vietnameseDescription;
    OrderStatusEnum(String description, String vietnameseDescription) {
        this.description = description;
        this.vietnameseDescription = vietnameseDescription;
    }
}

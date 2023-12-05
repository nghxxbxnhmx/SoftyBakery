package com.poly.dto.enums;

import lombok.Getter;
import lombok.Setter;


public enum AccountRoleEnum {
    USER("User Role"),
    MANAGER("Manager Role"),
    ADMIN("Admin Role"),
    SUPER_ADMIN("Super Admin Role");

    private final String description;

    AccountRoleEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
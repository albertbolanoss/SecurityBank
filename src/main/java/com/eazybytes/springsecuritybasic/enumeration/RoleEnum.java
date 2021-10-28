package com.eazybytes.springsecuritybasic.enumeration;

public enum RoleEnum {
    ADMINISTRATOR("ADMIN"),
    CUSTOMER("CUSTOMER");

    RoleEnum(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

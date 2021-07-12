package com.eazybytes.springsecuritybasic.enumeration;

public enum AuthorityEnum {
    CUSTOMER("customer"),
    WRITE("WRITE"),
    READ("READ");

    AuthorityEnum(String name) {
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

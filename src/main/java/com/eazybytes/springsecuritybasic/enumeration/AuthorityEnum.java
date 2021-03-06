package com.eazybytes.springsecuritybasic.enumeration;

public enum AuthorityEnum {
    ADMIN("admin"),
    READ("read");

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

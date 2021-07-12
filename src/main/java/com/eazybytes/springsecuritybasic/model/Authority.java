package com.eazybytes.springsecuritybasic.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "authorities")
public class Authority {
    @Id
    private String id;

    private Boolean enabled;

    public Authority() {
    }

    public Authority(int String, Boolean enabled) {
        this.id = id;
        this.enabled = enabled;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

}

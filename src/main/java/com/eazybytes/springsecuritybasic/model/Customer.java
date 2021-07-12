package com.eazybytes.springsecuritybasic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    private String email;
    @JsonIgnore
    private String pwd;
    private String role;
    private Boolean enabled;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<Permission> permissions;

    public Customer() {

    }

    public Customer(String email, String pwd, String role, Boolean enabled) {
        this.email = email;
        this.pwd = pwd;
        this.role = role;
        this.enabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}

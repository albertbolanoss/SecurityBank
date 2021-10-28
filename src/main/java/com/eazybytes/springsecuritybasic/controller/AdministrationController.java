package com.eazybytes.springsecuritybasic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class AdministrationController {
    @RequestMapping("/getAdminInfo")
    public ResponseEntity<Principal> getAdminInfo(Principal principal) {
        return new ResponseEntity<>(principal, HttpStatus.OK);
    }
}

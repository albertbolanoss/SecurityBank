package com.eazybytes.springsecuritybasic.controller;

import com.eazybytes.springsecuritybasic.dto.ContactMessageDto;
import com.eazybytes.springsecuritybasic.service.ContactMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {
    @Autowired
    private ContactMessageService contactMessageService;

    @PostMapping("/contact")
    public ResponseEntity<ContactMessageDto> saveContactInquiryDetails(@RequestBody ContactMessageDto contactMessage) {
        ResponseEntity responseEntity;
        try {
            ContactMessageDto contactMessageDto = contactMessageService.save(contactMessage);
            responseEntity = new ResponseEntity<>(contactMessageDto, HttpStatus.OK);
        } catch (RuntimeException runtimeException) {
            responseEntity = new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}

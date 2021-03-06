package com.eazybytes.springsecuritybasic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/news")
public class NoticeController {
    @GetMapping
    public String getNotices() {
        return "Notices";
    }
}

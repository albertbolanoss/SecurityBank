package com.eazybytes.springsecuritybasic.service;

import com.eazybytes.springsecuritybasic.dto.ContactMessageDto;

public interface ContactMessageService {
    ContactMessageDto save(ContactMessageDto contactMessageDto);
}

package com.eazybytes.springsecuritybasic.service.impl;

import com.eazybytes.springsecuritybasic.dto.ContactMessageDto;
import com.eazybytes.springsecuritybasic.mapper.ContactMessageMapper;
import com.eazybytes.springsecuritybasic.model.ContactMessage;
import com.eazybytes.springsecuritybasic.repository.ContactMessageRepository;
import com.eazybytes.springsecuritybasic.service.ContactMessageService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ContactMessageServiceImpl implements ContactMessageService {
    @Autowired
    private ContactMessageRepository contactMessageRepository;

    private ContactMessageMapper contactMessageMapper = Mappers.getMapper(ContactMessageMapper.class);

    @Override
    public ContactMessageDto save(ContactMessageDto contactMessageDto) {
        ContactMessage contactMessage = contactMessageMapper.convertToEntity(contactMessageDto);
        contactMessage.setCreated(LocalDateTime.now());

        return contactMessageMapper.convertToContactMessageDto(contactMessageRepository.save(contactMessage));
    }
}

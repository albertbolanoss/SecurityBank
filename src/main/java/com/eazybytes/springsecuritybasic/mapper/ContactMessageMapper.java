package com.eazybytes.springsecuritybasic.mapper;

import com.eazybytes.springsecuritybasic.dto.ContactMessageDto;
import com.eazybytes.springsecuritybasic.model.ContactMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ContactMessageMapper {
    @Mapping(target = "id", source = "contactId")
    @Mapping(target = "name", source = "contactName")
    @Mapping(target = "email", source = "contactEmail")
    ContactMessage convertToEntity(ContactMessageDto contactMessageDto);

    @Mapping(target = "contactId", source = "id")
    @Mapping(target = "contactName", source = "name")
    @Mapping(target = "contactEmail", source = "email")
    ContactMessageDto convertToContactMessageDto(ContactMessage contactMessage);
}

package com.eazybytes.springsecuritybasic.repository;

import com.eazybytes.springsecuritybasic.model.ContactMessage;
import org.springframework.data.repository.CrudRepository;

public interface ContactMessageRepository extends CrudRepository<ContactMessage, Long> {
}

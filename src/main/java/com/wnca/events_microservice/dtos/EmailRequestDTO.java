package com.wnca.events_microservice.dtos;

public record EmailRequestDTO(String to, String subject, String body) {
    // This record is used to represent an email request with a recipient, subject, and body.
    // It is a simple data transfer object (DTO) that can be used to send email information.
}

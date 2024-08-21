package com.tinqinacademy.email.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.email.api.operations.emaildetails.EmailDetailsOperationInput;
import com.tinqinacademy.email.api.apimappings.RestApiMappingEmail;
import com.tinqinacademy.email.core.processors.EmailDetailsOperationProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController extends BaseController {

    private final EmailDetailsOperationProcessor emailDetailsOperationProcessor;
    private final ObjectMapper objectMapper;

    @Autowired
    public EmailController(EmailDetailsOperationProcessor emailDetailsOperationProcessor, ObjectMapper objectMapper) {
        this.emailDetailsOperationProcessor = emailDetailsOperationProcessor;
        this.objectMapper = objectMapper;
    }

    @PostMapping(RestApiMappingEmail.POST_SENDEMAIL_PATH)
    public ResponseEntity<?> sendMail(@RequestBody EmailDetailsOperationInput input) {
        return handleOperation(emailDetailsOperationProcessor.process(input), HttpStatus.BAD_REQUEST);
    }
}
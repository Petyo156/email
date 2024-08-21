package com.tinqinacademy.email.restexport;

import com.tinqinacademy.email.api.apimappings.RestApiMappingEmail;
import com.tinqinacademy.email.api.operations.emaildetails.EmailDetailsOperationInput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "emailClient", url = "http://localhost:8083")
public interface EmailClient {
    @PostMapping(RestApiMappingEmail.POST_SENDEMAIL_PATH)
    ResponseEntity<?> sendMail(@RequestBody EmailDetailsOperationInput input);
}

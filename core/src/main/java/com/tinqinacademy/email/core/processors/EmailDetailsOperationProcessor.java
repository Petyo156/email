package com.tinqinacademy.email.core.processors;

import com.tinqinacademy.email.api.exceptions.Errors;
import com.tinqinacademy.email.api.operations.emaildetails.EmailDetailsOperation;
import com.tinqinacademy.email.api.operations.emaildetails.EmailDetailsOperationInput;
import com.tinqinacademy.email.api.operations.emaildetails.EmailDetailsOperationOutput;
import com.tinqinacademy.email.core.errorhandling.ErrorMapper;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Slf4j
@Service
public class EmailDetailsOperationProcessor extends BaseOperationProcessor implements EmailDetailsOperation {
    private final JavaMailSender javaMailSender;
    private final String sender;

    @Autowired
    public EmailDetailsOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper, Validator validator, JavaMailSender javaMailSender) {
        super(conversionService, errorMapper, validator);
        this.javaMailSender = javaMailSender;
        this.sender = "${email.sender}";
    }

    @Override
    public Either<Errors, EmailDetailsOperationOutput> process(EmailDetailsOperationInput input) {
        return Try.of(() -> {
                    log.info("Start emailDetails input: {}", input);

                    SimpleMailMessage mailMessage = new SimpleMailMessage();

                    mailMessage.setFrom(sender);
                    mailMessage.setTo(input.getRecipient());
                    mailMessage.setText(input.getMsgBody());
                    mailMessage.setSubject(input.getSubject());

                    javaMailSender.send(mailMessage);

                    EmailDetailsOperationOutput output = EmailDetailsOperationOutput.builder()
                            .build();

                    log.info("End emailDetails output: {}", output);
                    return output;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(instanceOf(Exception.class)), errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))
                ));
    }
}

package com.tinqinacademy.email.api.operations.emaildetails;

import com.tinqinacademy.email.api.base.OperationInput;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class EmailDetailsOperationInput implements OperationInput {
    private String recipient;
    private String msgBody;
    private String subject;
}
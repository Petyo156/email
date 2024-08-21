package com.tinqinacademy.email.api.base;

import com.tinqinacademy.email.api.exceptions.Errors;
import io.vavr.control.Either;

public interface OperationProcessor<S extends OperationInput, T extends OperationOutput> {
    Either<Errors, T> process(S input);
}

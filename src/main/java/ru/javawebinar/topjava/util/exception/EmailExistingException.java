package ru.javawebinar.topjava.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "User with this email already present in application")
public class EmailExistingException extends RuntimeException {
    public EmailExistingException() {
        super("User with this email already present in application");
    }
}

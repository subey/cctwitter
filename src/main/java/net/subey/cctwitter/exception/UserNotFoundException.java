package net.subey.cctwitter.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException {
    private final static HttpStatus status = HttpStatus.NOT_FOUND;
    private final static String message = "User %s not found.";

    public UserNotFoundException(String user) {
        super(status, String.format(message, user));
    }
}

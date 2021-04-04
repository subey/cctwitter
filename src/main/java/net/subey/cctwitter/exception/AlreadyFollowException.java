package net.subey.cctwitter.exception;

import org.springframework.http.HttpStatus;

public class AlreadyFollowException extends BaseException {
    private final static HttpStatus status = HttpStatus.BAD_REQUEST;
    private final static String message = "You already following %s.";

    public AlreadyFollowException(String user) {
        super(status, String.format(message, user));
    }
}

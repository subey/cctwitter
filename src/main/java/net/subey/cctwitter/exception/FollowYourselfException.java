package net.subey.cctwitter.exception;

import org.springframework.http.HttpStatus;

public class FollowYourselfException extends BaseException {
    private final static HttpStatus status = HttpStatus.BAD_REQUEST;
    private final static String message = "Can't follow yourself";

    public FollowYourselfException() {
        super(status, message);
    }
}

package net.subey.cctwitter.exception;

import org.springframework.http.HttpStatus;

public abstract class BaseException extends RuntimeException{
    HttpStatus status;
    public BaseException(){}

    public BaseException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

}

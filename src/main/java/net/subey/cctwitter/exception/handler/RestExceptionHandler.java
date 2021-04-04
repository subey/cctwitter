package net.subey.cctwitter.exception.handler;

import net.subey.cctwitter.exception.BaseException;
import net.subey.cctwitter.exception.dto.ApiError;
import net.subey.cctwitter.exception.dto.ApiErrorDetail;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;

@Order(Ordered.HIGHEST_PRECEDENCE)
@SuppressWarnings({"unchecked","rawtypes"})
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity getError(String msg, HttpStatus status){
        return new ResponseEntity<>(ApiError.builder().errors(
                Arrays.asList(ApiErrorDetail.builder()
                        .message(msg)
                        .build())
        ).build(), new HttpHeaders(), status);
    }
    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<ApiError> handleApiException(BaseException e){
        return getError(e.getMessage(),e.getStatus());
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError err = ApiError.builder().errors(new ArrayList<>()).build();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            if(error instanceof FieldError){
                err.getErrors().add(ApiErrorDetail.builder()
                        .message(String.format("%s: %s",
                                ((FieldError)error).getField(),
                                error.getDefaultMessage()))
                        .build());
            }else {
                err.getErrors().add(ApiErrorDetail.builder()
                        .message(error.getDefaultMessage())
                        .build());
            }
        }
        return new ResponseEntity(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception e, WebRequest request) {
        return getError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

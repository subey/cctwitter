package net.subey.cctwitter.exception.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiErrorDetail {
    private String message;
}

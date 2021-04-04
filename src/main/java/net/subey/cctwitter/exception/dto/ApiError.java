package net.subey.cctwitter.exception.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ApiError {
    private List<ApiErrorDetail> errors;
}

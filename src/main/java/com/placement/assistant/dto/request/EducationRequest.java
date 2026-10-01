package com.placement.assistant.dto.request;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data public class EducationRequest {
    @NotBlank private String degree;
    @NotBlank private String institution;
    private Integer year;
    private Double score;
}

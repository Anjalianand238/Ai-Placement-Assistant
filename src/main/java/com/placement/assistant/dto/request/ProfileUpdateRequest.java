package com.placement.assistant.dto.request;
import lombok.Data;
@Data public class ProfileUpdateRequest {
    private String name;
    private Double cgpa;
    private String branch;
    private Integer passoutYear;
}

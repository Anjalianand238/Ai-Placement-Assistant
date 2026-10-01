package com.placement.assistant.dto.request;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data public class CompanyRequest {
    @NotBlank private String name;
    private String website;
    private String description;
    private String logoUrl;
}

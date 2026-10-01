package com.placement.assistant.dto.request;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data public class SkillGapRequest {
    @NotBlank private String jobDescription;
    private String jobTitle;
}

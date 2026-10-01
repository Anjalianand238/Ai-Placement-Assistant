package com.placement.assistant.dto.request;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data public class ProjectRequest {
    @NotBlank private String title;
    private String description;
    private String techStack;
    private String githubUrl;
}

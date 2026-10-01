package com.placement.assistant.dto.request;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
@Data public class JobRequest {
    @NotBlank private String title;
    @NotBlank private String description;
    private String requiredSkills;
    private String location;
    private BigDecimal ctc;
    private LocalDate deadline;
    private Boolean isActive=true;
    @NotNull private Long companyId;
}

package com.placement.assistant.dto.request;
import com.placement.assistant.entity.Skill;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data public class SkillRequest {
    @NotBlank private String name;
    private Skill.Proficiency proficiency = Skill.Proficiency.BEGINNER;
}

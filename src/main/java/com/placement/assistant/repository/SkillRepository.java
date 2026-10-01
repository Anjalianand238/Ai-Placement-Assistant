package com.placement.assistant.repository;
import com.placement.assistant.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface SkillRepository extends JpaRepository<Skill,Long> {
    List<Skill> findByStudentProfileId(Long studentProfileId);
}

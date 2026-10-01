package com.placement.assistant.repository;
import com.placement.assistant.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface EducationRepository extends JpaRepository<Education,Long> {
    List<Education> findByStudentProfileId(Long studentProfileId);
}

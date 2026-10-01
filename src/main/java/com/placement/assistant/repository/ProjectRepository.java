package com.placement.assistant.repository;
import com.placement.assistant.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ProjectRepository extends JpaRepository<Project,Long> {
    List<Project> findByStudentProfileId(Long studentProfileId);
}

package com.placement.assistant.repository;
import com.placement.assistant.entity.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface StudentProfileRepository extends JpaRepository<StudentProfile,Long> {
    Optional<StudentProfile> findByUserId(Long userId);
    Optional<StudentProfile> findByUserEmail(String email);
}

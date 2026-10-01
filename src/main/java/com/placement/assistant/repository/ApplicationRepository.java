package com.placement.assistant.repository;
import com.placement.assistant.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
public interface ApplicationRepository extends JpaRepository<Application,Long> {
    List<Application> findByStudentProfileId(Long studentProfileId);
    boolean existsByStudentProfileIdAndJobId(Long studentProfileId,Long jobId);
    @Query("SELECT a FROM Application a JOIN FETCH a.job j JOIN FETCH j.company JOIN FETCH a.studentProfile sp JOIN FETCH sp.user ORDER BY a.appliedAt DESC")
    List<Application> findAllWithDetails();
    @Query("SELECT a FROM Application a JOIN FETCH a.job j JOIN FETCH j.company WHERE a.studentProfile.user.email=:email ORDER BY a.appliedAt DESC")
    List<Application> findByStudentEmail(String email);
}

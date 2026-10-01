package com.placement.assistant.repository;
import com.placement.assistant.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
public interface JobRepository extends JpaRepository<Job,Long> {
    @Query("SELECT j FROM Job j JOIN FETCH j.company WHERE j.isActive=true ORDER BY j.createdAt DESC")
    List<Job> findAllActiveJobs();
    @Query("SELECT j FROM Job j JOIN FETCH j.company ORDER BY j.createdAt DESC")
    List<Job> findAllWithCompany();
}

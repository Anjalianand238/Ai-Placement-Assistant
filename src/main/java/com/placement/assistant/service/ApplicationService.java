package com.placement.assistant.service;
import com.placement.assistant.entity.*;
import com.placement.assistant.exception.ResourceNotFoundException;
import com.placement.assistant.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service @RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository appRepo;
    private final StudentProfileRepository profileRepo;
    private final JobRepository jobRepo;

    public Application apply(String email,Long jobId){
        StudentProfile p=profileRepo.findByUserEmail(email).orElseThrow(()->new ResourceNotFoundException("Profile not found"));
        if(appRepo.existsByStudentProfileIdAndJobId(p.getId(),jobId)) throw new IllegalArgumentException("Already applied to this job");
        Job j=jobRepo.findById(jobId).orElseThrow(()->new ResourceNotFoundException("Job",jobId));
        return appRepo.save(Application.builder().studentProfile(p).job(j).build());
    }

    public List<Application> getMyApplications(String email){return appRepo.findByStudentEmail(email);}
    public List<Application> getAllApplications(){return appRepo.findAllWithDetails();}

    public Application updateStatus(Long appId,String status,String adminNote){
        Application a=appRepo.findById(appId).orElseThrow(()->new ResourceNotFoundException("Application",appId));
        a.setStatus(Application.Status.valueOf(status.toUpperCase()));
        if(adminNote!=null) a.setAdminNote(adminNote);
        return appRepo.save(a);
    }
}

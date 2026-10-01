package com.placement.assistant.service;
import com.placement.assistant.dto.request.JobRequest;
import com.placement.assistant.entity.*;
import com.placement.assistant.exception.ResourceNotFoundException;
import com.placement.assistant.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service @RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepo;
    private final CompanyRepository companyRepo;

    public List<Job> getAllActiveJobs(){return jobRepo.findAllActiveJobs();}
    public List<Job> getAllJobs(){return jobRepo.findAllWithCompany();}
    public Job getJobById(Long id){return jobRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Job",id));}

    public Job createJob(JobRequest req){
        Company c=companyRepo.findById(req.getCompanyId()).orElseThrow(()->new ResourceNotFoundException("Company",req.getCompanyId()));
        return jobRepo.save(Job.builder().title(req.getTitle()).description(req.getDescription()).requiredSkills(req.getRequiredSkills()).location(req.getLocation()).ctc(req.getCtc()).deadline(req.getDeadline()).isActive(req.getIsActive()).company(c).build());
    }

    public Job updateJob(Long id,JobRequest req){
        Job j=getJobById(id);
        Company c=companyRepo.findById(req.getCompanyId()).orElseThrow(()->new ResourceNotFoundException("Company",req.getCompanyId()));
        j.setTitle(req.getTitle()); j.setDescription(req.getDescription()); j.setRequiredSkills(req.getRequiredSkills());
        j.setLocation(req.getLocation()); j.setCtc(req.getCtc()); j.setDeadline(req.getDeadline()); j.setIsActive(req.getIsActive()); j.setCompany(c);
        return jobRepo.save(j);
    }

    public void deleteJob(Long id){jobRepo.delete(getJobById(id));}
}

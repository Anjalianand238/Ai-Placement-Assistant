package com.placement.assistant.controller;
import com.placement.assistant.dto.request.*;
import com.placement.assistant.entity.*;
import com.placement.assistant.repository.*;
import com.placement.assistant.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController @RequestMapping("/api/admin") @PreAuthorize("hasRole('ADMIN')") @RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final JobService jobService;
    private final ApplicationService applicationService;
    private final UserRepository userRepo;
    private final JobRepository jobRepo;
    private final ApplicationRepository appRepo;
    private final CompanyRepository companyRepo;

    @GetMapping("/dashboard") public ResponseEntity<Map<String,Object>> dashboard(){
        return ResponseEntity.ok(Map.of("totalStudents",userRepo.findAll().stream().filter(u->u.getRole()==User.Role.STUDENT).count(),"totalJobs",jobRepo.count(),"totalApplications",appRepo.count(),"totalCompanies",companyRepo.count()));
    }
    @GetMapping("/students") public ResponseEntity<List<User>> getStudents(){return ResponseEntity.ok(adminService.getAllStudents());}
    @GetMapping("/students/{id}") public ResponseEntity<StudentProfile> getStudent(@PathVariable Long id){return ResponseEntity.ok(adminService.getStudentById(id));}
    @GetMapping("/companies") public ResponseEntity<List<Company>> getCompanies(){return ResponseEntity.ok(adminService.getAllCompanies());}
    @PostMapping("/companies") public ResponseEntity<Company> createCompany(@Valid @RequestBody CompanyRequest req){return ResponseEntity.ok(adminService.createCompany(req));}
    @PutMapping("/companies/{id}") public ResponseEntity<Company> updateCompany(@PathVariable Long id,@Valid @RequestBody CompanyRequest req){return ResponseEntity.ok(adminService.updateCompany(id,req));}
    @DeleteMapping("/companies/{id}") public ResponseEntity<Map<String,String>> deleteCompany(@PathVariable Long id){adminService.deleteCompany(id); return ResponseEntity.ok(Map.of("message","Deleted"));}
    @GetMapping("/jobs") public ResponseEntity<List<Job>> getJobs(){return ResponseEntity.ok(jobService.getAllJobs());}
    @PostMapping("/jobs") public ResponseEntity<Job> createJob(@Valid @RequestBody JobRequest req){return ResponseEntity.ok(jobService.createJob(req));}
    @PutMapping("/jobs/{id}") public ResponseEntity<Job> updateJob(@PathVariable Long id,@Valid @RequestBody JobRequest req){return ResponseEntity.ok(jobService.updateJob(id,req));}
    @DeleteMapping("/jobs/{id}") public ResponseEntity<Map<String,String>> deleteJob(@PathVariable Long id){jobService.deleteJob(id); return ResponseEntity.ok(Map.of("message","Deleted"));}
    @GetMapping("/applications") public ResponseEntity<List<Application>> getApplications(){return ResponseEntity.ok(applicationService.getAllApplications());}
    @PutMapping("/applications/{id}/status") public ResponseEntity<Application> updateStatus(@PathVariable Long id,@RequestBody Map<String,String> body){
        return ResponseEntity.ok(applicationService.updateStatus(id,body.get("status"),body.get("adminNote")));
    }
}

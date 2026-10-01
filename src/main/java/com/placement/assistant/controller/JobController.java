package com.placement.assistant.controller;
import com.placement.assistant.entity.Job;
import com.placement.assistant.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController @RequestMapping("/api/jobs") @RequiredArgsConstructor
public class JobController {
    private final JobService jobService;
    private final ApplicationService applicationService;
    @GetMapping public ResponseEntity<List<Job>> getAllJobs(){return ResponseEntity.ok(jobService.getAllActiveJobs());}
    @GetMapping("/{id}") public ResponseEntity<Job> getJob(@PathVariable Long id){return ResponseEntity.ok(jobService.getJobById(id));}
    @PostMapping("/{id}/apply") public ResponseEntity<Map<String,String>> apply(@PathVariable Long id,Authentication auth){applicationService.apply(auth.getName(),id); return ResponseEntity.ok(Map.of("message","Application submitted!"));}
}

package com.placement.assistant.controller;
import com.placement.assistant.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController @RequestMapping("/api/ai") @RequiredArgsConstructor
public class AiController {
    private final GeminiAiService geminiAiService;
    private final StudentService studentService;
    private final JobService jobService;

    @PostMapping("/skill-gap")
    public ResponseEntity<Map<String,Object>> skillGap(Authentication auth,@RequestBody Map<String,String> body){
        var profile=studentService.getProfile(auth.getName());
        List<String> skills=profile.getSkills().stream().map(s->s.getName()).collect(Collectors.toList());
        return ResponseEntity.ok(geminiAiService.analyzeSkillGap(skills,body.get("jobDescription"),body.getOrDefault("jobTitle","Software Developer")));
    }

    @PostMapping("/parse-jd")
    public ResponseEntity<Map<String,Object>> parseJd(@RequestBody Map<String,String> body){
        return ResponseEntity.ok(geminiAiService.parseJobDescription(body.get("jobDescription")));
    }

    @PostMapping("/analyze-resume")
    public ResponseEntity<Map<String,Object>> analyzeResume(Authentication auth){
        var profile=studentService.getProfile(auth.getName());
        if(profile.getResumeText()==null||profile.getResumeText().isBlank()) return ResponseEntity.badRequest().body(Map.of("error","No resume uploaded yet"));
        return ResponseEntity.ok(geminiAiService.analyzeResume(profile.getResumeText()));
    }

    @PostMapping("/roadmap")
    public ResponseEntity<Map<String,Object>> roadmap(@RequestBody Map<String,Object> body){
        List<String> missing=(List<String>)body.getOrDefault("missingSkills",List.of());
        String targetRole=(String)body.getOrDefault("targetRole","Software Developer");
        return ResponseEntity.ok(geminiAiService.generateRoadmap(missing,targetRole));
    }

    @PostMapping("/interview-questions")
    public ResponseEntity<Map<String,Object>> interviewQs(Authentication auth,@RequestBody Map<String,String> body){
        var profile=studentService.getProfile(auth.getName());
        List<String> skills=profile.getSkills().stream().map(s->s.getName()).collect(Collectors.toList());
        return ResponseEntity.ok(geminiAiService.generateInterviewQuestions(body.get("jobTitle"),skills));
    }

    @PostMapping("/job-match/{jobId}")
    public ResponseEntity<Map<String,Object>> jobMatch(Authentication auth,@PathVariable Long jobId){
        var profile=studentService.getProfile(auth.getName());
        var job=jobService.getJobById(jobId);
        List<String> skills=profile.getSkills().stream().map(s->s.getName()).collect(Collectors.toList());
        return ResponseEntity.ok(geminiAiService.calculateJobMatch(skills,job.getDescription(),job.getTitle()));
    }
}

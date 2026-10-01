package com.placement.assistant.controller;
import com.placement.assistant.dto.request.*;
import com.placement.assistant.entity.*;
import com.placement.assistant.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.nio.file.*;
import java.util.Map;
import java.util.UUID;

@RestController @RequestMapping("/api/student") @RequiredArgsConstructor @PreAuthorize("hasRole('STUDENT')")
public class StudentController {
    private final StudentService studentService;
    private final ResumeParserService resumeParserService;
    @Value("${app.upload.dir}") private String uploadDir;

    @GetMapping("/profile") public ResponseEntity<StudentProfile> getProfile(Authentication auth){return ResponseEntity.ok(studentService.getProfile(auth.getName()));}
    @PutMapping("/profile") public ResponseEntity<StudentProfile> updateProfile(Authentication auth,@RequestBody ProfileUpdateRequest req){return ResponseEntity.ok(studentService.updateProfile(auth.getName(),req));}
    @PostMapping("/skills") public ResponseEntity<Skill> addSkill(Authentication auth,@Valid @RequestBody SkillRequest req){return ResponseEntity.ok(studentService.addSkill(auth.getName(),req));}
    @DeleteMapping("/skills/{id}") public ResponseEntity<Map<String,String>> deleteSkill(Authentication auth,@PathVariable Long id){studentService.deleteSkill(auth.getName(),id); return ResponseEntity.ok(Map.of("message","Skill deleted"));}
    @PostMapping("/projects") public ResponseEntity<Project> addProject(Authentication auth,@Valid @RequestBody ProjectRequest req){return ResponseEntity.ok(studentService.addProject(auth.getName(),req));}
    @DeleteMapping("/projects/{id}") public ResponseEntity<Map<String,String>> deleteProject(Authentication auth,@PathVariable Long id){studentService.deleteProject(auth.getName(),id); return ResponseEntity.ok(Map.of("message","Project deleted"));}
    @PostMapping("/education") public ResponseEntity<Education> addEducation(Authentication auth,@Valid @RequestBody EducationRequest req){return ResponseEntity.ok(studentService.addEducation(auth.getName(),req));}
    @DeleteMapping("/education/{id}") public ResponseEntity<Map<String,String>> deleteEducation(Authentication auth,@PathVariable Long id){studentService.deleteEducation(auth.getName(),id); return ResponseEntity.ok(Map.of("message","Education deleted"));}

    @PostMapping("/resume")
    public ResponseEntity<Map<String,String>> uploadResume(Authentication auth,@RequestParam("file") MultipartFile file) throws Exception {
        new File(uploadDir).mkdirs();
        String filename=UUID.randomUUID()+".pdf";
        Path path=Paths.get(uploadDir,filename);
        Files.copy(file.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
        String text=resumeParserService.extractText(file);
        studentService.updateResume(auth.getName(),"/uploads/"+filename,text);
        return ResponseEntity.ok(Map.of("message","Resume uploaded","url","/uploads/"+filename));
    }
}

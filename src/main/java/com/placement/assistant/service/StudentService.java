package com.placement.assistant.service;
import com.placement.assistant.dto.request.*;
import com.placement.assistant.entity.*;
import com.placement.assistant.exception.ResourceNotFoundException;
import com.placement.assistant.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class StudentService {
    private final StudentProfileRepository profileRepo;
    private final UserRepository userRepo;
    private final SkillRepository skillRepo;
    private final ProjectRepository projectRepo;
    private final EducationRepository educationRepo;

    public StudentProfile getProfile(String email){
        return profileRepo.findByUserEmail(email).orElseThrow(()->new ResourceNotFoundException("Profile not found"));
    }

    @Transactional
    public StudentProfile updateProfile(String email,ProfileUpdateRequest req){
        StudentProfile p=getProfile(email);
        if(req.getName()!=null){p.getUser().setName(req.getName()); userRepo.save(p.getUser());}
        if(req.getCgpa()!=null) p.setCgpa(req.getCgpa());
        if(req.getBranch()!=null) p.setBranch(req.getBranch());
        if(req.getPassoutYear()!=null) p.setPassoutYear(req.getPassoutYear());
        return profileRepo.save(p);
    }

    public Skill addSkill(String email,SkillRequest req){
        StudentProfile p=getProfile(email);
        Skill s=Skill.builder().name(req.getName()).proficiency(req.getProficiency()).studentProfile(p).build();
        return skillRepo.save(s);
    }

    public void deleteSkill(String email,Long skillId){
        StudentProfile p=getProfile(email);
        Skill s=skillRepo.findById(skillId).orElseThrow(()->new ResourceNotFoundException("Skill",skillId));
        if(!s.getStudentProfile().getId().equals(p.getId())) throw new IllegalArgumentException("Not your skill");
        skillRepo.delete(s);
    }

    public Project addProject(String email,ProjectRequest req){
        StudentProfile p=getProfile(email);
        Project proj=Project.builder().title(req.getTitle()).description(req.getDescription()).techStack(req.getTechStack()).githubUrl(req.getGithubUrl()).studentProfile(p).build();
        return projectRepo.save(proj);
    }

    public void deleteProject(String email,Long id){
        StudentProfile p=getProfile(email);
        Project proj=projectRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Project",id));
        if(!proj.getStudentProfile().getId().equals(p.getId())) throw new IllegalArgumentException("Not your project");
        projectRepo.delete(proj);
    }

    public Education addEducation(String email,EducationRequest req){
        StudentProfile p=getProfile(email);
        Education e=Education.builder().degree(req.getDegree()).institution(req.getInstitution()).year(req.getYear()).score(req.getScore()).studentProfile(p).build();
        return educationRepo.save(e);
    }

    public void deleteEducation(String email,Long id){
        StudentProfile p=getProfile(email);
        Education e=educationRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Education",id));
        if(!e.getStudentProfile().getId().equals(p.getId())) throw new IllegalArgumentException("Not your record");
        educationRepo.delete(e);
    }

    @Transactional
    public StudentProfile updateResume(String email,String resumeUrl,String resumeText){
        StudentProfile p=getProfile(email);
        p.setResumeUrl(resumeUrl);
        p.setResumeText(resumeText);
        return profileRepo.save(p);
    }
}

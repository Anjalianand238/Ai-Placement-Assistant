package com.placement.assistant.service;
import com.placement.assistant.dto.request.CompanyRequest;
import com.placement.assistant.entity.*;
import com.placement.assistant.exception.ResourceNotFoundException;
import com.placement.assistant.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service @RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepo;
    private final StudentProfileRepository profileRepo;
    private final CompanyRepository companyRepo;

    public List<User> getAllStudents(){return userRepo.findAll().stream().filter(u->u.getRole()==User.Role.STUDENT).toList();}
    public StudentProfile getStudentById(Long userId){return profileRepo.findByUserId(userId).orElseThrow(()->new ResourceNotFoundException("Student profile",userId));}
    public List<Company> getAllCompanies(){return companyRepo.findAll();}
    public Company createCompany(CompanyRequest req){return companyRepo.save(Company.builder().name(req.getName()).website(req.getWebsite()).description(req.getDescription()).logoUrl(req.getLogoUrl()).build());}
    public Company updateCompany(Long id,CompanyRequest req){
        Company c=companyRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Company",id));
        c.setName(req.getName()); c.setWebsite(req.getWebsite()); c.setDescription(req.getDescription()); c.setLogoUrl(req.getLogoUrl());
        return companyRepo.save(c);
    }
    public void deleteCompany(Long id){companyRepo.delete(companyRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Company",id)));}
}

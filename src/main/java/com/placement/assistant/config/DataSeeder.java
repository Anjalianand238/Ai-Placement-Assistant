package com.placement.assistant.config;
import com.placement.assistant.entity.*;
import com.placement.assistant.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;

@Component @RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    @Value("${app.upload.dir}") private String uploadDir;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final JobRepository jobRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        new File(uploadDir).mkdirs();
        if(!userRepository.existsByEmail("admin@placement.com")){
            userRepository.save(User.builder().name("Admin").email("admin@placement.com")
                .password(passwordEncoder.encode("admin123")).role(User.Role.ADMIN).build());
        }
        if(companyRepository.count()==0){
            Company tcs=companyRepository.save(Company.builder().name("TCS").website("tcs.com").description("India's largest IT services company").build());
            Company inf=companyRepository.save(Company.builder().name("Infosys").website("infosys.com").description("Global leader in next-generation digital services").build());
            Company wip=companyRepository.save(Company.builder().name("Wipro").website("wipro.com").description("Leading technology services company").build());
            jobRepository.save(Job.builder().title("Java Backend Developer").company(tcs).requiredSkills("Java, Spring Boot, MySQL, REST API").description("We are looking for a strong Java backend developer with Spring Boot experience.").ctc(new BigDecimal("4.5")).location("Bangalore").deadline(LocalDate.now().plusMonths(6)).isActive(true).build());
            jobRepository.save(Job.builder().title("Full Stack Developer").company(inf).requiredSkills("React, Node.js, Java, Spring Boot, MySQL").description("Join our team as a Full Stack Developer working on enterprise applications.").ctc(new BigDecimal("5.5")).location("Pune").deadline(LocalDate.now().plusMonths(6)).isActive(true).build());
            jobRepository.save(Job.builder().title("Software Engineer").company(wip).requiredSkills("Java, DSA, Python, SQL").description("Looking for smart engineers with strong problem-solving skills.").ctc(new BigDecimal("3.5")).location("Hyderabad").deadline(LocalDate.now().plusMonths(6)).isActive(true).build());
        }
    }
}

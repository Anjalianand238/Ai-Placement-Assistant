package com.placement.assistant.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name="student_profiles") @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class StudentProfile {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @OneToOne @JoinColumn(name="user_id",nullable=false)
    @ToString.Exclude @EqualsAndHashCode.Exclude private User user;
    private Double cgpa;
    private String branch;
    private Integer passoutYear;
    private String resumeUrl;
    @Column(columnDefinition="LONGTEXT") private String resumeText;
    @OneToMany(mappedBy="studentProfile",cascade=CascadeType.ALL,orphanRemoval=true)
    @Builder.Default @ToString.Exclude @EqualsAndHashCode.Exclude private List<Skill> skills=new ArrayList<>();
    @OneToMany(mappedBy="studentProfile",cascade=CascadeType.ALL,orphanRemoval=true)
    @Builder.Default @ToString.Exclude @EqualsAndHashCode.Exclude private List<Project> projects=new ArrayList<>();
    @OneToMany(mappedBy="studentProfile",cascade=CascadeType.ALL,orphanRemoval=true)
    @Builder.Default @ToString.Exclude @EqualsAndHashCode.Exclude private List<Education> educations=new ArrayList<>();
    @OneToMany(mappedBy="studentProfile",cascade=CascadeType.ALL)
    @Builder.Default @ToString.Exclude @EqualsAndHashCode.Exclude private List<Application> applications=new ArrayList<>();
}

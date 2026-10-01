package com.placement.assistant.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="projects") @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Project {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String title;
    @Column(columnDefinition="TEXT") private String description;
    private String techStack;
    private String githubUrl;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="student_profile_id")
    @ToString.Exclude @EqualsAndHashCode.Exclude private StudentProfile studentProfile;
}

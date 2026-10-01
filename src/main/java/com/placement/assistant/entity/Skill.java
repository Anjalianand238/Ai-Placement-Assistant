package com.placement.assistant.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="skills") @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Skill {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String name;
    @Enumerated(EnumType.STRING) @Builder.Default private Proficiency proficiency=Proficiency.BEGINNER;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="student_profile_id")
    @ToString.Exclude @EqualsAndHashCode.Exclude private StudentProfile studentProfile;
    public enum Proficiency { BEGINNER, INTERMEDIATE, ADVANCED }
}

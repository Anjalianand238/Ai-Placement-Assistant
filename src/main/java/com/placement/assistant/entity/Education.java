package com.placement.assistant.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="education") @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Education {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String degree;
    @Column(nullable=false) private String institution;
    private Integer year;
    private Double score;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="student_profile_id")
    @ToString.Exclude @EqualsAndHashCode.Exclude private StudentProfile studentProfile;
}

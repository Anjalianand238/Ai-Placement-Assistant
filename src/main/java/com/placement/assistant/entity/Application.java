package com.placement.assistant.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity @Table(name="applications",uniqueConstraints={@UniqueConstraint(columnNames={"student_profile_id","job_id"})})
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Application {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="student_profile_id",nullable=false)
    @ToString.Exclude @EqualsAndHashCode.Exclude private StudentProfile studentProfile;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="job_id",nullable=false)
    @ToString.Exclude @EqualsAndHashCode.Exclude private Job job;
    @Enumerated(EnumType.STRING) @Builder.Default private Status status=Status.APPLIED;
    @CreationTimestamp @Column(updatable=false) private LocalDateTime appliedAt;
    private String adminNote;
    public enum Status { APPLIED, SHORTLISTED, REJECTED, HIRED }
}

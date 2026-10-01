package com.placement.assistant.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name="jobs") @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Job {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String title;
    @Column(columnDefinition="TEXT") private String description;
    @Column(columnDefinition="TEXT") private String requiredSkills;
    private String location;
    private BigDecimal ctc;
    private LocalDate deadline;
    @Builder.Default private Boolean isActive=true;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="company_id")
    @ToString.Exclude @EqualsAndHashCode.Exclude private Company company;
    @OneToMany(mappedBy="job",cascade=CascadeType.ALL)
    @Builder.Default @ToString.Exclude @EqualsAndHashCode.Exclude private List<Application> applications=new ArrayList<>();
    @CreationTimestamp @Column(updatable=false) private LocalDateTime createdAt;
}

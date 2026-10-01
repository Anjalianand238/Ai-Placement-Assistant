package com.placement.assistant.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name="companies") @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Company {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String name;
    private String website;
    @Column(columnDefinition="TEXT") private String description;
    private String logoUrl;
    @OneToMany(mappedBy="company",cascade=CascadeType.ALL)
    @Builder.Default @ToString.Exclude @EqualsAndHashCode.Exclude private List<Job> jobs=new ArrayList<>();
}

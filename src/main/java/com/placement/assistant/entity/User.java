package com.placement.assistant.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity @Table(name="users") @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String name;
    @Column(nullable=false,unique=true) private String email;
    @Column(nullable=false) private String password;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private Role role;
    @CreationTimestamp @Column(updatable=false) private LocalDateTime createdAt;
    @OneToOne(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @ToString.Exclude @EqualsAndHashCode.Exclude private StudentProfile studentProfile;
    public enum Role { STUDENT, ADMIN }
}

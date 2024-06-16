package com.rosada.crud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String fullName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserCrud userCrud;
}

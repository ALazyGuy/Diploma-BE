package com.ltp.diploma.diplomabe.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "userentity")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password_hash;
}

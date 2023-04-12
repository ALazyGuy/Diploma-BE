package com.ltp.diploma.diplomabe.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password_hash;
    @OneToMany(fetch = FetchType.LAZY)
    private Set<TestEntity> tests = new HashSet<>();
}

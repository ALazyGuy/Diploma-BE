package com.ltp.diploma.diplomabe.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class TestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int result;
    private int ticket;
}

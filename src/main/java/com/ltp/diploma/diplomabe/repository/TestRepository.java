package com.ltp.diploma.diplomabe.repository;

import com.ltp.diploma.diplomabe.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Long> {
}

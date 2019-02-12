package com.example.demo.casbinrule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;

@Repository
public interface CasbinRuleRepository extends JpaRepository<CasbinRule, Long> {

    @Transactional
    @Modifying
    int deleteByV0AndV1(String name, String dept);
}


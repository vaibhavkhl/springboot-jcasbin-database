package com.example.demo.casbinrule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
public class CasbinRuleController {
    @Autowired
    private CasbinRuleRepository casbinRuleRepository;


    @GetMapping("/casbinrules")
    public List<CasbinRule> getCasbinRules() {
        return casbinRuleRepository.findAll();
    }

    @PostMapping("/casbinrules")
    public CasbinRule createCasbinRule(@Valid @RequestBody CasbinRule casbinRule) {
        return casbinRuleRepository.save(casbinRule);
    }

    @DeleteMapping("/casbinrules")
    public int deleteRule(HttpServletRequest casbinRule) {

        System.out.println("--------------------------" + casbinRule.getParameter("v0"));
        return casbinRuleRepository.deleteByV0AndV1(casbinRule.getParameter("v0"), casbinRule.getParameter("v1"));


    }
}

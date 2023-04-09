package com.ltp.diploma.diplomabe.controller;

import com.ltp.diploma.diplomabe.service.RulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rules")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.OPTIONS})
public class RulesController {

    private final RulesService rulesService;

    @Autowired
    public RulesController(RulesService rulesService) {
        this.rulesService = rulesService;
    }

    @GetMapping("/{id}")
    public String loadRulesById(@PathVariable String id) {
        return rulesService.loadRulesById(id);
    }

    @GetMapping("/nav/{id}")
    public String loadNavigationById(@PathVariable String id) {
        return rulesService.loadRulesNavigation(id);
    }
}

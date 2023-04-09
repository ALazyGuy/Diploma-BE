package com.ltp.diploma.diplomabe.service;

import org.springframework.stereotype.Service;

@Service
public interface RulesService {
    String loadRulesById(String id);
    String loadRulesNavigation(String id);
}

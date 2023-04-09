package com.ltp.diploma.diplomabe.service.impl;

import com.ltp.diploma.diplomabe.service.RulesService;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class RulesServiceImpl implements RulesService {

    private static final String PPD_RULE_REQUEST_URL = "https://pdd.by/pdd/ru/";

    @SneakyThrows
    @Override
    public String loadRulesById(final String id) {
        return getByUrl(PPD_RULE_REQUEST_URL + id).getElementById("show_results").html()
                .replaceAll("(href=\"https://pdd.by/([0-9]|\\.)+\")", "")
                .replaceAll("(src=\")", "src=\"https://pdd.by");
    }

    @SneakyThrows
    @Override
    public String loadRulesNavigation(final String id) {
        return getByUrl(PPD_RULE_REQUEST_URL + id).getElementsByClass("b-rules_submenu").get(0).html();
    }

    @SneakyThrows
    private Document getByUrl(final String url){
        return Jsoup.connect(url).get();
    }

}

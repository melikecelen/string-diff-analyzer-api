package com.melikecelen.stringdiffanalyzer.api.controller;

import com.melikecelen.stringdiffanalyzer.service.StringDiffService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;


@RestController
@CrossOrigin
public class StringDiffController implements StringDiffOperations {

    private StringDiffService service;

    public StringDiffController(StringDiffService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<String> diffAnalyzerGet(String s1, String s2) {
        if(s1.isBlank() || s2.isBlank()){
            return ResponseEntity.badRequest().body("Required parameter(s) is empty: "
                    + (s1.isBlank() ? "s1 " : "") + (s2.isBlank() ? "s2" : ""));
        }
        String result = service.mix(s1, s2);
        return ResponseEntity.ok("{\"result\" : \""+ result +"\"}");
    }


}

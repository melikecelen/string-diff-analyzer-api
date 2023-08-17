package com.melikecelen.stringdiffanalyzer.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


public interface StringDiffOperations {

    @Operation(summary = "String diff analyzer", description = "String diff analyzer", tags = {"String Diff Analyzer"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success"),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request"),
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error")
    })
    @GetMapping(value = "/string-diff-analyzer/v1/diff",
            produces = {"application/json"})
    ResponseEntity<String> diffAnalyzerGet(@RequestParam("s1") String s1,
                                           @RequestParam("s2") String s2);

}

package com.melikecelen.stringdiffanalyzer.integration;

import com.melikecelen.stringdiffanalyzer.StringDiffAnalyzerApiApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(
        classes = StringDiffAnalyzerApiApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StringDiffAnalyzerApiIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String s1 = "mmmmm m nnnnn y&friend&Paul has heavy hats! &";
    private final String s2 = "my frie n d Joh n has ma n y ma n y frie n ds n&";


    @Test
    void testBothInputsWithLowercaseChars() {

        String baseUrl = "http://localhost:" + port;

        URI uri = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/string-diff-analyzer/v1/diff")
                .queryParam("s1", s1)
                .queryParam("s2", s2)
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();

        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("{" +
                        "\"result\" : " +
                        "\"1:mmmmmm/=:nnnnnn/1:aaaa/1:hhh/2:yyy/2:dd/2:ff/2:ii/2:rr/=:ee/=:ss\"" +
                        "}",
                response.getBody());
    }

    @Test
    void testWithOneEmptyInput() {

        String baseUrl = "http://localhost:" + port;
        URI uri = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/string-diff-analyzer/v1/diff")
                .queryParam("s1", "")
                .queryParam("s2", "AAaBCDeFG")
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();

        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

        assertEquals(400, response.getStatusCode().value());
        assertTrue(response.getBody().contains("Required parameter(s) is empty: s1"));
    }

    @Test
    void testWithTwoEmptyInputs() {

        String baseUrl = "http://localhost:" + port;
        URI uri = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/string-diff-analyzer/v1/diff")
                .queryParam("s1", "")
                .queryParam("s2", "")
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();

        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

        assertEquals(400, response.getStatusCode().value());
        assertTrue(response.getBody().contains("Required parameter(s) is empty: s1 s2"));
    }

    @Test
    void testInputIsNull() {

        String baseUrl = "http://localhost:" + port;

        //s1 did not been provided.
        URI uri = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/string-diff-analyzer/v1/diff")
                .queryParam("s2", "AAaBCDeFG")
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();

        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

        assertEquals(400, response.getStatusCode().value());
        assertTrue(response.getBody().contains("Required parameter 's1' is missing."));
    }
}
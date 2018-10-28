package com.julia.demo2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.boot.test.web.client.TestRestTemplate;


@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = Main.class
)
@AutoConfigureMockMvc
public class IntegrationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void noFramesAtTheBeginningOfTheGame() throws Exception {

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/game/")
                .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        System.out.println("contextLoads");
        System.out.println("response status = " + mvcResult.getResponse().getStatus());
        System.out.println("response content = " + mvcResult.getResponse().getContentAsString()); // empty Collection

    }

    @Test
    public void callingScoreAtTheBeginningOfTheGameThrowsError() throws Exception {
        try{
            MvcResult mvcResult = mockMvc.perform(
                    MockMvcRequestBuilders.get("/game/score/")
                            .accept(MediaType.APPLICATION_JSON)
            ).andReturn();
        } catch (Exception e) {
            System.out.println("score throws Error as expected:");
            System.out.println("error message = " + e.getMessage());
        }
    }

    @LocalServerPort
    private int port;
    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();

    @Test
    public void roll() throws Exception {

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        //http://localhost:8080/game/roll?pins=1 // poszlo z postmana przy typie JSON
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost/game/roll/roll?pins=1", HttpMethod.PUT, entity, String.class);
        String expected = "{id:1,roll1:1,roll2:0,roll1Done=true,roll2Done=true}";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }


}

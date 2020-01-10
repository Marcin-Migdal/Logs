package com.project.logs

import com.fasterxml.jackson.databind.ObjectMapper
import com.project.logs.payload.JwtAuthenticationResponse
import com.project.logs.payload.LoginRequest
import com.project.logs.payload.SignUpRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AcceptanceSpec extends Specification {

    @Autowired
    MockMvc mockMvc

    @Autowired
    ObjectMapper objectMapper

    def "Integration test"() {
        when: "Adam should be able to register"
            SignUpRequest signUpRequest = SignUpRequest.builder()
                    .username("adam_nowak")
                    .password("Ala123")
                    .name("Adam Nowak")
                    .email("jj@gmail.com")
                    .build()
            ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                    .post("/api/auth/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(signUpRequest)))
        then: "Adam is registered"
            result.andExpect(MockMvcResultMatchers.status().isCreated())
            result.andExpect(MockMvcResultMatchers.content().json("""
            {
                "success":true,
                "message":"User registered successfully"
            }
            """))

        when: "Adam should be able to log in"
            def loginRequest = LoginRequest.builder()
                    .usernameOrEmail("adam_nowak")
                    .password("Ala123")
                    .build()
            result = mockMvc.perform(MockMvcRequestBuilders
                    .post("/api/auth/signin")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(loginRequest)))
        then: "Adam is logged in"
            result.andExpect(MockMvcResultMatchers.status().isOk())
            result.andExpect(MockMvcResultMatchers.content().json("""
            {
                "tokenType":"Bearer"
            }
            """))



        when: "Adam should be able to see logs from 2020-04-20"
            String jwtString = result.andReturn().getResponse().getContentAsString()
            JwtAuthenticationResponse jwt = objectMapper.readValue(jwtString, JwtAuthenticationResponse.class)
            def token = "Bearer " + jwt.accessToken

            result = mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/logs/byDate/2020-04-20")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", token))
        then: ""
            result.andExpect(MockMvcResultMatchers.status().isOk())
    }

}

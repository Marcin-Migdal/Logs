package com.example.logs


import com.example.logs.payload.SignUpRequest
import com.fasterxml.jackson.databind.ObjectMapper
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

    def "asd"() {
        when: ""
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
        then: ""
            result.andExpect(MockMvcResultMatchers.status().isCreated())
            result.andExpect(MockMvcResultMatchers.content().json("""
            {
                "success":true,
                "message":"User registered successfully"
            }
            """))
    }
}

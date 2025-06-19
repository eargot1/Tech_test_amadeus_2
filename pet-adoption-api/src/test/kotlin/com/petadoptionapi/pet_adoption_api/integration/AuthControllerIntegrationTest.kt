package com.petadoptionapi.pet_adoption_api.integration

import com.fasterxml.jackson.databind.ObjectMapper
import com.petadoptionapi.pet_adoption_api.dto.LoginRequestDto
import com.petadoptionapi.pet_adoption_api.model.Role
import com.petadoptionapi.pet_adoption_api.model.User
import com.petadoptionapi.pet_adoption_api.repository.user.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = [
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.datasource.url=jdbc:h2:mem:integrationtest"
])
class AuthControllerIntegrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var objectMapper: ObjectMapper

    fun serializeToString(any: Any): String = objectMapper.writeValueAsString(any)

    @BeforeEach
    fun setUp() {
        val admin = User(
            id = 1,
            email = "admin@test.com",
            name = "Admin",
            role = Role.ADMIN,
            password = passwordEncoder.encode("admin123")
        )
        userRepository.save(admin)
    }

    @Test
    fun `Login gets token correctly`(){
        val loginRequest = LoginRequestDto(
            email = "admin@test.com",
            password = "admin123"
        )

        val request = serializeToString(loginRequest)

        mockMvc.perform(
            MockMvcRequestBuilders
                .post("/auth/login")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON),
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.token").isNotEmpty)

    }

    @Test
    fun `Login returns 404 for non-existent user`() {
        val loginRequest = LoginRequestDto(
            email = "nonexistent@test.com",
            password = "admin123"
        )

        val request = serializeToString(loginRequest)

        mockMvc.perform(
            MockMvcRequestBuilders
                .post("/auth/login")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }
}
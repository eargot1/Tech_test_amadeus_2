package com.petadoptionapi.pet_adoption_api.integration

import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.JsonPath
import com.petadoptionapi.pet_adoption_api.commons.Id
import com.petadoptionapi.pet_adoption_api.dto.PetCreateRequestDto
import com.petadoptionapi.pet_adoption_api.dto.PetStatusRequestDto
import com.petadoptionapi.pet_adoption_api.model.Role
import com.petadoptionapi.pet_adoption_api.model.Species
import com.petadoptionapi.pet_adoption_api.model.Status
import com.petadoptionapi.pet_adoption_api.model.User
import com.petadoptionapi.pet_adoption_api.repository.pet.PetRepository
import com.petadoptionapi.pet_adoption_api.repository.user.UserRepository
import com.petadoptionapi.pet_adoption_api.security.JwtUtils
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
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
class PetControllerIntegrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var petRepository: PetRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var jwtUtils: JwtUtils

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
    fun `get all pets correctly`(){
        petRepository.create(
            PetCreateRequestDto(
                name = "test",
                age = 12,
                species = Species.CAT.name
            )
        )

        val adminUser = userRepository.getByEmail("admin@test.com")
        val token = jwtUtils.generateToken(adminUser!!.email)

        val headers = HttpHeaders()
        headers.setBearerAuth(token)

        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/pets")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON),
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty)

    }

    @Test
    fun `get single pet correctly`(){
        val id = petRepository.create(
            PetCreateRequestDto(
                name = "test",
                age = 12,
                species = Species.CAT.name
            )
        )

        val adminUser = userRepository.getByEmail("admin@test.com")
        val token = jwtUtils.generateToken(adminUser!!.email)

        val headers = HttpHeaders()
        headers.setBearerAuth(token)

        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/pets/${id.getValue()}")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON),
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty)

    }

    @Test
    fun `update status correctly`(){
        val id = petRepository.create(
            PetCreateRequestDto(
                name = "test",
                age = 12,
                species = Species.CAT.name
            )
        )

        val adminUser = userRepository.getByEmail("admin@test.com")
        val token = jwtUtils.generateToken(adminUser!!.email)

        val request = serializeToString(PetStatusRequestDto(
            id.getValue(), Status.IN_ADOPTION_PROCESS.name
        ))

        val headers = HttpHeaders()
        headers.setBearerAuth(token)

        mockMvc.perform(
            MockMvcRequestBuilders
                .put("/pets/status")
                .content(request)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON),
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty)

        val result = petRepository.get(id)
        Assertions.assertEquals(result?.status.toString(), Status.IN_ADOPTION_PROCESS.name)

    }

    @Test
    fun `create pet correctly`(){
        val adminUser = userRepository.getByEmail("admin@test.com")
        val token = jwtUtils.generateToken(adminUser!!.email)

        val request = serializeToString(PetCreateRequestDto(
            "test", 12, Species.DOG.name
        ))

        val headers = HttpHeaders()
        headers.setBearerAuth(token)

        mockMvc.perform(
            MockMvcRequestBuilders
                .post("/pets")
                .content(request)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON),
        ).andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty)
            .andReturn()

    }

    @Test
    fun `delete single pet correctly`(){
        val id = petRepository.create(
            PetCreateRequestDto(
                name = "test",
                age = 12,
                species = Species.CAT.name
            )
        )

        val adminUser = userRepository.getByEmail("admin@test.com")
        val token = jwtUtils.generateToken(adminUser!!.email)

        val headers = HttpHeaders()
        headers.setBearerAuth(token)

        mockMvc.perform(
            MockMvcRequestBuilders
                .delete("/pets/delete/${id.getValue()}")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON),
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty)

    }

}
package com.petadoptionapi.pet_adoption_api.configuration

import com.petadoptionapi.pet_adoption_api.model.Role
import com.petadoptionapi.pet_adoption_api.model.User
import com.petadoptionapi.pet_adoption_api.repository.user.UserRepository
import jakarta.annotation.PostConstruct
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class DataInitializer(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @PostConstruct
    fun initData() {
        if (userRepository.getByEmail("admin@test.com")==null) {
            val admin = User(
                id = 1,
                email = "admin@test.com",
                name = "Admin",
                role = Role.ADMIN,
                password = passwordEncoder.encode("admin123")
            )
            userRepository.save(admin)
        }

        if (userRepository.getByEmail("client@test.com")==null) {
            val client = User(
                id = 2,
                email = "client@test.com",
                name = "Client",
                role = Role.CLIENT,
                password = passwordEncoder.encode("password123")
            )
            userRepository.save(client)
        }
    }
}
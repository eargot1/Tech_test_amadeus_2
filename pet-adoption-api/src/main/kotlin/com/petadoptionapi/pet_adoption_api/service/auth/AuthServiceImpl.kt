package com.petadoptionapi.pet_adoption_api.service.auth

import com.petadoptionapi.pet_adoption_api.dto.LoginRequestDto
import com.petadoptionapi.pet_adoption_api.dto.LoginResponseDto
import com.petadoptionapi.pet_adoption_api.exceptions.LoginException
import com.petadoptionapi.pet_adoption_api.exceptions.NotFoundException
import com.petadoptionapi.pet_adoption_api.repository.user.UserRepository
import com.petadoptionapi.pet_adoption_api.security.JwtUtils
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    val userRepository: UserRepository,
    val jwtUtils: JwtUtils,
    private val passwordEncoder: PasswordEncoder
): AuthService{

    override fun login(request: LoginRequestDto): LoginResponseDto? {

        var token: String
        val response = userRepository.getByEmail(request.email)?: throw NotFoundException("User was not found")
        if(passwordEncoder.matches(request.password,response.password)){
            token = jwtUtils.generateToken(request.email)
        } else throw LoginException("Error at login")
        return LoginResponseDto(
            response.role.name, token
        )
    }
}
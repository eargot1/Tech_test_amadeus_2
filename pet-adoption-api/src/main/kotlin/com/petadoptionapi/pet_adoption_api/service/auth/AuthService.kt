package com.petadoptionapi.pet_adoption_api.service.auth

import com.petadoptionapi.pet_adoption_api.dto.LoginRequestDto
import com.petadoptionapi.pet_adoption_api.dto.LoginResponseDto

interface AuthService {

    fun login(request: LoginRequestDto): LoginResponseDto?

}
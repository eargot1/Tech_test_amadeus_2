package com.petadoptionapi.pet_adoption_api.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Response del login")
data class LoginResponseDto(
    @Schema(description = "Rol del usuario con dicho id", example = "Cliente")
    val name: String,
    @Schema(description = "JWT Token", example = "eyJhbGciOiJIUzI1NiJ9...")
    val token: String
)
package com.petadoptionapi.pet_adoption_api.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Request para login")
data class LoginRequestDto(
    @Schema(description = "Email del usuario", example = "admin@example.com")
    val email: String,
    @Schema(description = "Contraseña", example = "admin123")
    val password: String
)

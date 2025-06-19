package com.petadoptionapi.pet_adoption_api.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "DTO para actualizar el estado de una mascota existente")
data class PetStatusRequestDto(
    @Schema(
        description = "ID de la mascota que se va a actualizar",
        example = "1",
        required = true
    )
    val id: Long,

    @Schema(
        description = "Nuevo estado que se asignará a la mascota",
        example = "ADOPTED",
        required = true,
        allowableValues = ["IN_SHELTER", "IN_ADOPTION_PROCESS", "ADOPTED"]
    )
    val newStatus: String
)
package com.petadoptionapi.pet_adoption_api.dto

import com.petadoptionapi.pet_adoption_api.exceptions.NameNotValidException
import com.petadoptionapi.pet_adoption_api.exceptions.SpeciesNotValidException
import com.petadoptionapi.pet_adoption_api.model.Species
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "DTO para crear una nueva mascota en el sistema")
data class PetCreateRequestDto(
    @Schema(
        description = "Nombre de la mascota",
        example = "Pepe",
        required = true,
        minLength = 1,
        maxLength = 50
    )
    val name: String,

    @Schema(
        description = "Edad de la mascota en años",
        example = "3",
        required = true,
        minimum = "0",
        maximum = "25"
    )
    val age: Long,

    @Schema(
        description = "Especie de la mascota",
        example = "DOG",
        required = true,
        allowableValues = ["DOG", "CAT"]
    )
    val species: String
) {
    init {
        if (name == "") throw NameNotValidException("the name is invalid")
        try {
            Species.valueOf(species)
        } catch (e: IllegalArgumentException) {
            throw SpeciesNotValidException("the species is not valid")
        }
    }
}
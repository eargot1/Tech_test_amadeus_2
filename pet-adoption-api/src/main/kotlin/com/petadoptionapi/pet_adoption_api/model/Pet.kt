package com.petadoptionapi.pet_adoption_api.model

import com.petadoptionapi.pet_adoption_api.commons.Id
import jakarta.validation.constraints.Positive
import java.time.Instant

data class Pet(
    val id: Long,
    val name: String,
    @Positive
    val age: Long,
    val species: String,
    val status: String,
    val entryDate: Instant,
)

enum class Status{
    ADOPTED,
    IN_ADOPTION_PROCESS,
    IN_SHELTER
}

enum class Species{
    DOG,
    CAT
}
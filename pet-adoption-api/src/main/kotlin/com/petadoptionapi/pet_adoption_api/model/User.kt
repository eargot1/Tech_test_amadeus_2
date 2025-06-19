package com.petadoptionapi.pet_adoption_api.model

import com.petadoptionapi.pet_adoption_api.commons.Id

data class User(
    val id: Long,
    val name: String,
    val email: String,
    val role: Role,
    val password: String,
)

enum class Role {
    ADMIN,
    CLIENT
}
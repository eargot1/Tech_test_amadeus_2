package com.petadoptionapi.pet_adoption_api.repository.user

import com.petadoptionapi.pet_adoption_api.commons.Id
import com.petadoptionapi.pet_adoption_api.dto.PetCreateRequestDto
import com.petadoptionapi.pet_adoption_api.model.Pet
import com.petadoptionapi.pet_adoption_api.model.User

interface UserRepository {

    fun getByEmail(email: String): User?

    fun save(user: User)
}
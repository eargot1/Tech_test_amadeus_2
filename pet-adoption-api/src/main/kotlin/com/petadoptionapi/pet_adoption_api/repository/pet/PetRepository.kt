package com.petadoptionapi.pet_adoption_api.repository.pet

import com.petadoptionapi.pet_adoption_api.commons.Id
import com.petadoptionapi.pet_adoption_api.dto.PetCreateRequestDto
import com.petadoptionapi.pet_adoption_api.dto.PetStatusRequestDto
import com.petadoptionapi.pet_adoption_api.model.Pet

interface PetRepository {

    fun get(id: Id): Pet?

    fun getAll(): List<Pet>

    fun create(pet: PetCreateRequestDto): Id

    fun update(pet: Pet)

    fun delete(id: Id)
}
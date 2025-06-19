package com.petadoptionapi.pet_adoption_api.service.pet

import com.petadoptionapi.pet_adoption_api.commons.Id
import com.petadoptionapi.pet_adoption_api.dto.PetCreateRequestDto
import com.petadoptionapi.pet_adoption_api.dto.PetStatusRequestDto
import com.petadoptionapi.pet_adoption_api.model.Pet

interface PetService {

    fun createPet(pet: PetCreateRequestDto): Id

    fun getPets(): List<Pet>

    fun getPet(id: Id): Pet

    fun deletePet(id: Id)

    fun updatePetStatus(request: PetStatusRequestDto)
}
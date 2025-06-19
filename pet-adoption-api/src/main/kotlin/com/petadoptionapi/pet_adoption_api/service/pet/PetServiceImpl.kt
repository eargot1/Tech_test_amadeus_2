package com.petadoptionapi.pet_adoption_api.service.pet

import com.petadoptionapi.pet_adoption_api.commons.Id
import com.petadoptionapi.pet_adoption_api.dto.PetCreateRequestDto
import com.petadoptionapi.pet_adoption_api.dto.PetStatusRequestDto
import com.petadoptionapi.pet_adoption_api.exceptions.NotFoundException
import com.petadoptionapi.pet_adoption_api.model.Pet
import com.petadoptionapi.pet_adoption_api.model.Status
import com.petadoptionapi.pet_adoption_api.repository.pet.PetRepository
import org.springframework.stereotype.Service

@Service
class PetServiceImpl(
    val petRepository: PetRepository
): PetService{

    override fun createPet(pet: PetCreateRequestDto): Id {
        val id = petRepository.create(pet)
        return id
    }

    override fun getPets(): List<Pet>{
        val response = petRepository.getAll()
        if(response.isEmpty().not()){
            return response
        }else{
            throw NotFoundException("There are not pets on database")
        }
    }

    override fun getPet(id: Id): Pet {
        return petRepository.get(id)?: throw NotFoundException("Pet with id ${id.getValue()} not found")
    }

    override fun deletePet(id: Id) {
        petRepository.delete(id)
    }

    override fun updatePetStatus(request: PetStatusRequestDto) {
        val actual = petRepository.get(Id(request.id))?: throw NotFoundException("Pet with id ${request.id} not found")
        petRepository.update(Pet(
            actual.id,
            actual.name,
            actual.age,
            actual.species,
            Status.valueOf(request.newStatus).name,
            actual.entryDate
        ))
    }

}
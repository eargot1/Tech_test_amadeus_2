package com.petadoptionapi.pet_adoption_api.repository.pet

import com.petadoptionapi.pet_adoption_api.commons.Id
import com.petadoptionapi.pet_adoption_api.dto.PetCreateRequestDto
import com.petadoptionapi.pet_adoption_api.dto.PetStatusRequestDto
import com.petadoptionapi.pet_adoption_api.exceptions.NotFoundException
import com.petadoptionapi.pet_adoption_api.model.Pet
import com.petadoptionapi.pet_adoption_api.model.Status
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
class PetRepositoryImpl(
    val jpa: JpaPetRepository
): PetRepository{
    override fun get(id: Id): Pet? {
        val result = jpa.findById(id.getValue())
        if(result.isPresent.not()){
            return null
        }
        val petResult = result.get()
        with(petResult){
            return Pet(
                id.getValue(),
                name,
                age,
                species,
                status,
                entryDate
            )
        }
    }

    override fun getAll(): List<Pet> {
        return jpa.findAll().map {
            entity -> Pet(
            entity.id,
                entity.name,
                entity.age,
                entity.species,
                entity.status,
                entity.entryDate
            )
        }
    }

    override fun create(pet: PetCreateRequestDto): Id{
            val saved = jpa.save(PetEntity(
                name = pet.name,
                age = pet.age,
                species = pet.species,
                status = Status.IN_SHELTER.name,
                entryDate = Instant.now()
            ))

        return Id(saved.id)
    }

    override fun update(pet: Pet){
        with(pet){
            jpa.save(
                PetEntity(
                    id,
                    name,
                    age,
                    species,
                    status,
                    entryDate
                )
            )
        }
    }

    override fun delete(id: Id) {
        jpa.deleteById(id.getValue())
    }
}
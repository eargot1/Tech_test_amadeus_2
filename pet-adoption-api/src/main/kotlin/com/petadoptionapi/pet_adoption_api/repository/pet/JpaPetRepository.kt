package com.petadoptionapi.pet_adoption_api.repository.pet

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaPetRepository: JpaRepository<PetEntity, Long>
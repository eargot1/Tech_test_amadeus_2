package com.petadoptionapi.pet_adoption_api.repository.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaUserRepository: JpaRepository<UserEntity, Long>{
    fun findByEmail(email: String): UserEntity?
}
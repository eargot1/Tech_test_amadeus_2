package com.petadoptionapi.pet_adoption_api.repository.user

import com.petadoptionapi.pet_adoption_api.commons.Id
import com.petadoptionapi.pet_adoption_api.dto.PetCreateRequestDto
import com.petadoptionapi.pet_adoption_api.model.Pet
import com.petadoptionapi.pet_adoption_api.model.Role
import com.petadoptionapi.pet_adoption_api.model.Status
import com.petadoptionapi.pet_adoption_api.model.User
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
class UserRepositoryImpl(
    val jpa: JpaUserRepository
): UserRepository{
    override fun getByEmail(email: String): User? {
        val result = jpa.findByEmail(email)
        if(result == null){
            return null
        }
        with(result){
            return User(
                id,
                name,
                email,
                Role.valueOf(role),
                password
            )
        }
    }

    override fun save(user: User) {
        with(user){
            jpa.save(UserEntity(
                id,
                name,
                email,
                role.name,
                password
            ))
        }
    }
}
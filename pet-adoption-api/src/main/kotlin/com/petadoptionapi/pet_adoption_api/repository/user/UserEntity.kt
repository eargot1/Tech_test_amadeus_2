package com.petadoptionapi.pet_adoption_api.repository.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.validator.constraints.Range
import java.io.Serializable
import java.time.Instant

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    val id: Long = 0,
    @Column(name = "name", nullable = false, length = 15)
    val name: String,
    @Column(name = "email", nullable = false, length = 50)
    val email: String,
    @Column(name = "role", nullable = false, length = 8)
    val role: String,
    @Column(name = "password", nullable = false)
    val password: String,
) : Serializable {
    constructor() : this(0, "","", "","")
}
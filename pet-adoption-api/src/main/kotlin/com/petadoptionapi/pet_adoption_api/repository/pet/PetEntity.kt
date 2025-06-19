package com.petadoptionapi.pet_adoption_api.repository.pet

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
@Table(name = "pets")
data class PetEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "name", nullable = false, length = 15)
    val name: String,
    @Column(name = "age")
    @Range(min = 0, max = 25)
    val age: Long,
    @Column(name = "species", nullable = false, length = 8)
    val species: String,
    @Column(name = "status", nullable = false, length = 20)
    val status: String,
    @Column(name = "entry_date", nullable = false)
    val entryDate: Instant,
) : Serializable {
    constructor() : this(0, "",0, "","",Instant.now())
}
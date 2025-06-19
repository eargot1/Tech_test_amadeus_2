package com.petadoptionapi.pet_adoption_api.commons

import com.petadoptionapi.pet_adoption_api.exceptions.IdNotValidException

data class Id(
    private val value: Long,
) {
    init {
        if (value <= 0) throw IdNotValidException("id $value not valid")
    }

    fun getValue(): Long = value;
}

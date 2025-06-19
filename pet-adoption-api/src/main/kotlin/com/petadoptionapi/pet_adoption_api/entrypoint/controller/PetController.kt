package com.petadoptionapi.pet_adoption_api.entrypoint.controller

import com.petadoptionapi.pet_adoption_api.commons.Id
import com.petadoptionapi.pet_adoption_api.dto.PetCreateRequestDto
import com.petadoptionapi.pet_adoption_api.dto.PetStatusRequestDto
import com.petadoptionapi.pet_adoption_api.model.Pet
import com.petadoptionapi.pet_adoption_api.service.pet.PetService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pets")
@Tag(name = "Pets", description = "API para gestión de mascotas en el sistema de adopción")
class PetController(
    val petService: PetService
) {

    @GetMapping
    @Operation(
        summary = "Obtener todas las mascotas",
        description = "Retorna una lista de todas las mascotas registradas en base de datos."
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Lista de mascotas obtenida exitosamente",
            content = [Content(
                mediaType = "application/json",
                array = ArraySchema(schema = Schema(implementation = Pet::class))
            )]
        )
    ])
    fun getAllPets(): ResponseEntity<List<Pet>> {
        return ResponseEntity(petService.getPets(), HttpStatus.OK)
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener mascota por ID",
        description = "Retorna los detalles de una mascota específica basada en su id."
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Mascota encontrada exitosamente",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = Pet::class)
            )]
        ),
        ApiResponse(
            responseCode = "404",
            description = "Mascota no encontrada",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = String::class)
            )]
        )
    ])
    fun getSinglePet(
        @PathVariable
        @Parameter(
            description = "ID único de la mascota",
            example = "1",
            required = true
        )
        id: Long
    ): ResponseEntity<Pet> {
        return ResponseEntity(petService.getPet(Id(id)), HttpStatus.OK)
    }

    @PutMapping("/status")
    @Operation(
        summary = "Actualizar estado de mascota",
        description = "Permite actualizar el estado de una mascota (disponible, adoptada, en proceso)",
        security = [SecurityRequirement(name = "Bearer Authentication")]
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Estado de la mascota actualizado exitosamente",
            content = [Content(
                mediaType = "text/plain",
                schema = Schema(implementation = String::class, example = "Pet updated with id 1")
            )]
        ),
        ApiResponse(
            responseCode = "401",
            description = "No autenticado - Token JWT requerido"
        ),
        ApiResponse(
            responseCode = "403",
            description = "Sin permisos - Requiere rol ADMIN"
        ),
        ApiResponse(
            responseCode = "404",
            description = "Mascota no encontrada"
        )
    ])
    fun updatePetStatus(
        @RequestBody
        @Parameter(
            description = "Datos para actualizar el estado de la mascota",
            required = true
        )
        request: PetStatusRequestDto
    ): ResponseEntity<Any> {
        petService.updatePetStatus(request)
        return ResponseEntity("Pet updated with id ${request.id}", HttpStatus.OK)
    }

    @PostMapping
    @Operation(
        summary = "Crear nueva mascota",
        description = "Registra una nueva mascota en el sistema de adopción.",
        security = [SecurityRequirement(name = "Bearer Authentication")]
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "201",
            description = "Mascota creada exitosamente",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = Pet::class)
            )]
        ),
        ApiResponse(
            responseCode = "400",
            description = "Datos de entrada inválidos"
        ),
        ApiResponse(
            responseCode = "401",
            description = "No autenticado - Token JWT requerido"
        ),
        ApiResponse(
            responseCode = "403",
            description = "Sin permisos - Requiere rol ADMIN"
        )
    ])
    fun createPet(
        @RequestBody
        @Parameter(
            description = "Datos de la nueva mascota a registrar",
            required = true
        )
        pet: PetCreateRequestDto
    ): ResponseEntity<Any> {
        val response = petService.createPet(pet)
        return ResponseEntity("Pet created with id ${response.getValue()}", HttpStatus.CREATED)
    }

    @DeleteMapping("/delete/{id}")
    @Operation(
        summary = "Eliminar mascota",
        description = "Elimina una mascota del sistema basada en su ID. Requiere autenticación y permisos de ADMIN",
        security = [SecurityRequirement(name = "Bearer Authentication")]
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Mascota eliminada exitosamente",
            content = [Content(
                mediaType = "text/plain",
                schema = Schema(implementation = String::class, example = "Pet deleted with id 1")
            )]
        ),
        ApiResponse(
            responseCode = "401",
            description = "No autenticado - Token JWT requerido"
        ),
        ApiResponse(
            responseCode = "403",
            description = "Sin permisos - Requiere rol ADMIN"
        ),
        ApiResponse(
            responseCode = "404",
            description = "Mascota no encontrada"
        )
    ])
    fun deletePet(
        @PathVariable
        @Parameter(
            description = "ID de la mascota a eliminar",
            example = "1",
            required = true
        )
        id: Long
    ): ResponseEntity<Any> {
        petService.deletePet(Id(id))
        return ResponseEntity("Pet deleted with id $id", HttpStatus.OK)
    }
}
package com.petadoptionapi.pet_adoption_api.entrypoint.controller

import com.petadoptionapi.pet_adoption_api.dto.LoginRequestDto
import com.petadoptionapi.pet_adoption_api.dto.LoginResponseDto
import com.petadoptionapi.pet_adoption_api.entrypoint.handler.RequestFailure
import com.petadoptionapi.pet_adoption_api.service.auth.AuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints de autenticación")
class AuthController(
    val authService: AuthService
){

    @PostMapping("/login")
    @Operation(
        summary = "Iniciar sesión",
        description = "Autenticar usuario y obtener JWT token"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Login exitoso",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = LoginResponseDto::class)
            )]
        ),
        ApiResponse(
            responseCode = "401",
            description = "Credenciales inválidas",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = RequestFailure::class)
            )]
        ),
        ApiResponse(
            responseCode = "404",
            description = "Usuario no encontrado",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = RequestFailure::class)
            )]
        )
    ])
    fun login(
        @RequestBody request: LoginRequestDto
    ): ResponseEntity<LoginResponseDto>{
        println(request)
        val response = authService.login(request)
        println("Se retorno el usuario")
        return ResponseEntity(response, HttpStatus.OK)

    }
}
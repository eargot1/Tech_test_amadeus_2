package com.petadoptionapi.pet_adoption_api.entrypoint.handler

import com.petadoptionapi.pet_adoption_api.exceptions.IdNotValidException
import com.petadoptionapi.pet_adoption_api.exceptions.LoginException
import com.petadoptionapi.pet_adoption_api.exceptions.NameNotValidException
import com.petadoptionapi.pet_adoption_api.exceptions.NotFoundException
import com.petadoptionapi.pet_adoption_api.exceptions.SpeciesNotValidException
import io.swagger.v3.oas.annotations.Hidden
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
@Hidden
class ControllerExceptionHandler {
    companion object {
        private val logger = LoggerFactory.getLogger(ControllerExceptionHandler::class.java)
    }

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponse(
        responseCode = "404",
        description = "Recurso no encontrado",
        content = [Content(
            mediaType = "application/json",
            schema = Schema(implementation = RequestFailure::class)
        )]
    )
    fun handleNotFoundError(e: Exception): RequestFailure {
        return handleError(HttpStatus.NOT_FOUND.value(), e)
    }

    @ExceptionHandler(LoginException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ApiResponse(
        responseCode = "401",
        description = "Error de autenticación",
        content = [Content(
            mediaType = "application/json",
            schema = Schema(implementation = RequestFailure::class)
        )]
    )
    fun handleLoginError(e: Exception): RequestFailure {
        return handleError(HttpStatus.UNAUTHORIZED.value(), e)
    }

    @ExceptionHandler(IdNotValidException::class, NameNotValidException::class, SpeciesNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponse(
        responseCode = "400",
        description = "Datos de entrada inválidos",
        content = [Content(
            mediaType = "application/json",
            schema = Schema(implementation = RequestFailure::class)
        )]
    )
    fun handleBadRequestError(e: Exception): RequestFailure {
        return handleError(HttpStatus.BAD_REQUEST.value(), e)
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleUncontrolledExceptions(e: Exception): RequestFailure {
        return handleError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e)
    }

    private fun handleError(
        responseStatus: Int,
        error: Throwable,
    ): RequestFailure {
        logger.error("There was an error [response_status=$responseStatus] [error=$error]")
        return RequestFailure(error.message ?: error.toString())
    }
}

@Schema(description = "Estructura de respuesta para errores de la API")
data class RequestFailure(
    @Schema(
        description = "Mensaje de error"
    )
    val error: String
)
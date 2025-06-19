package com.petadoptionapi.pet_adoption_api.security

import com.petadoptionapi.pet_adoption_api.model.Role
import com.petadoptionapi.pet_adoption_api.model.User
import com.petadoptionapi.pet_adoption_api.repository.user.UserRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class AuthInterceptor(
    private val jwtUtils: JwtUtils,
    private val userRepository: UserRepository
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {

        if (isPublicEndpoint(request)) {
            return true
        }

        val token = extractToken(request)
        if (token == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token required")
            return false
        }

        if (!jwtUtils.validateToken(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token")
            return false
        }

        val user = getUserFromToken(token)
        if (user == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found")
            return false
        }

        if (!hasPermission(request, user.role)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Insufficient permissions")
            return false
        }

        request.setAttribute("currentUser", user)
        return true
    }

    private fun isPublicEndpoint(request: HttpServletRequest): Boolean {
        return when {
            request.requestURI == "/auth/login" -> true
            request.requestURI == "/pets" && request.method == "GET" -> true
            request.requestURI.startsWith("/swagger-ui") -> true
            request.requestURI.startsWith("/v3/api-docs") -> true
            request.requestURI == "/swagger-ui.html" -> true
            request.requestURI.startsWith("/webjars/") -> true
            request.method == "OPTIONS" -> true
            else -> false
        }
    }

    private fun extractToken(request: HttpServletRequest): String? {
        val authHeader = request.getHeader("Authorization")
        return if (authHeader?.startsWith("Bearer ") == true) {
            authHeader.substring(7)
        } else null
    }

    private fun getUserFromToken(token: String): User? {
        val email = jwtUtils.getUsernameFromToken(token)
        return userRepository.getByEmail(email ?: "")
    }

    private fun hasPermission(request: HttpServletRequest, role: Role): Boolean {
        val path = request.requestURI
        val method = request.method

        return when {
            path.startsWith("/pets") && method in listOf("POST", "PUT", "DELETE") -> {
                role == Role.ADMIN
            }

            path.startsWith("/pets/status") && method == "PUT" -> {
                role == Role.CLIENT
            }

            else -> {
                true
            }
        }
    }
}
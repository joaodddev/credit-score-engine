package br.com.joaodddev.creditscoreengine.application

import br.com.joaodddev.creditscoreengine.config.JwtService
import br.com.joaodddev.creditscoreengine.domain.User
import br.com.joaodddev.creditscoreengine.infrastructure.UserRepository
import br.com.joaodddev.creditscoreengine.web.dto.AuthRequest
import br.com.joaodddev.creditscoreengine.web.dto.AuthResponse
import br.com.joaodddev.creditscoreengine.web.dto.RegisterRequest
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager
) {

    fun register(request: RegisterRequest): AuthResponse {
        if (userRepository.findByEmail(request.email) != null)
            throw IllegalArgumentException("Email already registered")

        val user = User(
            email = request.email,
            password = passwordEncoder.encode(request.password)
        )

        userRepository.save(user)
        val token = jwtService.generateToken(user.email)
        return AuthResponse(token = token, email = user.email)
    }

    fun login(request: AuthRequest): AuthResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.email, request.password)
        )

        val user = userRepository.findByEmail(request.email)
            ?: throw NoSuchElementException("User not found")

        val token = jwtService.generateToken(user.email)
        return AuthResponse(token = token, email = user.email)
    }
}
package br.com.joaodddev.creditscoreengine.web.controller

import br.com.joaodddev.creditscoreengine.application.AuthService
import br.com.joaodddev.creditscoreengine.web.dto.AuthRequest
import br.com.joaodddev.creditscoreengine.web.dto.AuthResponse
import br.com.joaodddev.creditscoreengine.web.dto.RegisterRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Endpoints de autenticação")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/register")
    @Operation(summary = "Registrar novo usuário")
    fun register(@RequestBody @Valid request: RegisterRequest): ResponseEntity<AuthResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request))

    @PostMapping("/login")
    @Operation(summary = "Autenticar usuário e obter token JWT")
    fun login(@RequestBody @Valid request: AuthRequest): ResponseEntity<AuthResponse> =
        ResponseEntity.ok(authService.login(request))
}
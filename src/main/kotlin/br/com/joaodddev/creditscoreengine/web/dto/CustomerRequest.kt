package br.com.joaodddev.creditscoreengine.web.dto

import jakarta.validation.constraints.*

data class CustomerRequest(

    @field:NotBlank(message = "CPF is required")
    @field:Size(min = 11, max = 11, message = "CPF must have 11 digits")
    val cpf: String,

    @field:NotBlank(message = "Name is required")
    val name: String,

    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Invalid email format")
    val email: String,

    @field:Positive(message = "Monthly income must be positive")
    val monthlyIncome: Double
)
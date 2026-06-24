package br.com.joaodddev.creditscoreengine.web.dto

import br.com.joaodddev.creditscoreengine.domain.FinancialEventType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class FinancialEventRequest(

    @field:NotNull(message = "Customer ID is required")
    val customerId: Long,

    @field:NotNull(message = "Event type is required")
    val type: FinancialEventType,

    @field:Positive(message = "Amount must be positive")
    val amount: Double,

    @field:NotBlank(message = "Description is required")
    val description: String
)
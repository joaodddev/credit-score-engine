package br.com.joaodddev.creditscoreengine.web.dto

import br.com.joaodddev.creditscoreengine.domain.FinancialEvent
import br.com.joaodddev.creditscoreengine.domain.FinancialEventType
import java.time.LocalDateTime

data class FinancialEventResponse(
    val id: Long?,
    val customerId: Long?,
    val type: FinancialEventType,
    val amount: Double,
    val description: String,
    val occurredAt: LocalDateTime
) {
    companion object {
        fun from(event: FinancialEvent) = FinancialEventResponse(
            id = event.id,
            customerId = event.customer.id,
            type = event.type,
            amount = event.amount,
            description = event.description,
            occurredAt = event.occurredAt
        )
    }
}
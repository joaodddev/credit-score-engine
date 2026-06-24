package br.com.joaodddev.creditscoreengine.web.dto

import br.com.joaodddev.creditscoreengine.domain.Customer
import java.time.LocalDateTime

data class CustomerResponse(
    val id: Long?,
    val cpf: String,
    val name: String,
    val email: String,
    val monthlyIncome: Double,
    val createdAt: LocalDateTime
) {
    companion object {
        fun from(customer: Customer) = CustomerResponse(
            id = customer.id,
            cpf = customer.cpf,
            name = customer.name,
            email = customer.email,
            monthlyIncome = customer.monthlyIncome,
            createdAt = customer.createdAt
        )
    }
}
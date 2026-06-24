package br.com.joaodddev.creditscoreengine.application

import br.com.joaodddev.creditscoreengine.domain.FinancialEvent
import br.com.joaodddev.creditscoreengine.domain.ScoreEngine
import br.com.joaodddev.creditscoreengine.infrastructure.CustomerRepository
import br.com.joaodddev.creditscoreengine.infrastructure.FinancialEventRepository
import br.com.joaodddev.creditscoreengine.web.dto.FinancialEventRequest
import br.com.joaodddev.creditscoreengine.web.dto.FinancialEventResponse
import br.com.joaodddev.creditscoreengine.web.dto.ScoreResponse
import org.springframework.stereotype.Service

@Service
class FinancialEventService(
    private val financialEventRepository: FinancialEventRepository,
    private val customerRepository: CustomerRepository,
    private val scoreEngine: ScoreEngine
) {

    fun create(request: FinancialEventRequest): FinancialEventResponse {
        val customer = customerRepository.findById(request.customerId)
            .orElseThrow { NoSuchElementException("Customer not found with id: ${request.customerId}") }

        val event = FinancialEvent(
            customer = customer,
            type = request.type,
            amount = request.amount,
            description = request.description
        )

        return FinancialEventResponse.from(financialEventRepository.save(event))
    }

    fun findAllByCustomer(customerId: Long): List<FinancialEventResponse> {
        if (!customerRepository.existsById(customerId))
            throw NoSuchElementException("Customer not found with id: $customerId")

        return financialEventRepository.findAllByCustomerId(customerId)
            .map { FinancialEventResponse.from(it) }
    }

    fun calculateScore(customerId: Long): ScoreResponse {
        val customer = customerRepository.findById(customerId)
            .orElseThrow { NoSuchElementException("Customer not found with id: $customerId") }

        val events = financialEventRepository.findAllByCustomerId(customerId)
        val score = scoreEngine.calculate(events)
        val classification = scoreEngine.classify(score)

        return ScoreResponse(
            customerId = customer.id!!,
            customerName = customer.name,
            score = score,
            classification = classification,
            totalEvents = events.size
        )
    }
}
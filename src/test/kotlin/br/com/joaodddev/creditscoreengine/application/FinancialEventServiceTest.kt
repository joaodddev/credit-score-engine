package br.com.joaodddev.creditscoreengine.application

import br.com.joaodddev.creditscoreengine.domain.*
import br.com.joaodddev.creditscoreengine.infrastructure.CustomerRepository
import br.com.joaodddev.creditscoreengine.infrastructure.FinancialEventRepository
import br.com.joaodddev.creditscoreengine.web.dto.FinancialEventRequest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class FinancialEventServiceTest {

    private val financialEventRepository = mockk<FinancialEventRepository>()
    private val customerRepository = mockk<CustomerRepository>()
    private val scoreEngine = ScoreEngine()
    private val financialEventService = FinancialEventService(
        financialEventRepository, customerRepository, scoreEngine
    )

    @Test
    fun `should create financial event successfully`() {
        val customer = buildCustomer()
        val request = buildRequest()
        val event = buildEvent(customer)

        every { customerRepository.findById(1L) } returns Optional.of(customer)
        every { financialEventRepository.save(any()) } returns event

        val response = financialEventService.create(request)

        assertEquals(FinancialEventType.PAYMENT_ON_TIME, response.type)
        assertEquals(1L, response.customerId)
        verify(exactly = 1) { financialEventRepository.save(any()) }
    }

    @Test
    fun `should throw when customer not found on create event`() {
        val request = buildRequest()

        every { customerRepository.findById(1L) } returns Optional.empty()

        assertThrows<NoSuchElementException> {
            financialEventService.create(request)
        }
    }

    @Test
    fun `should return all events by customer`() {
        val customer = buildCustomer()

        every { customerRepository.existsById(1L) } returns true
        every { financialEventRepository.findAllByCustomerId(1L) } returns listOf(
            buildEvent(customer),
            buildEvent(customer)
        )

        val result = financialEventService.findAllByCustomer(1L)

        assertEquals(2, result.size)
    }

    @Test
    fun `should throw when customer not found on find events`() {
        every { customerRepository.existsById(99L) } returns false

        assertThrows<NoSuchElementException> {
            financialEventService.findAllByCustomer(99L)
        }
    }

    @Test
    fun `should calculate score correctly`() {
        val customer = buildCustomer()

        every { customerRepository.findById(1L) } returns Optional.of(customer)
        every { financialEventRepository.findAllByCustomerId(1L) } returns listOf(
            buildEvent(customer, FinancialEventType.PAYMENT_ON_TIME),
            buildEvent(customer, FinancialEventType.PAYMENT_ON_TIME)
        )

        val response = financialEventService.calculateScore(1L)

        assertEquals(600, response.score)
        assertEquals(ScoreClassification.MEDIUM, response.classification)
        assertEquals(2, response.totalEvents)
    }

    @Test
    fun `should return base score when no events`() {
        val customer = buildCustomer()

        every { customerRepository.findById(1L) } returns Optional.of(customer)
        every { financialEventRepository.findAllByCustomerId(1L) } returns emptyList()

        val response = financialEventService.calculateScore(1L)

        assertEquals(500, response.score)
        assertEquals(ScoreClassification.MEDIUM, response.classification)
        assertEquals(0, response.totalEvents)
    }

    @Test
    fun `should throw when customer not found on calculate score`() {
        every { customerRepository.findById(99L) } returns Optional.empty()

        assertThrows<NoSuchElementException> {
            financialEventService.calculateScore(99L)
        }
    }

    private fun buildCustomer() = Customer(
        id = 1L,
        cpf = "12345678901",
        name = "João Dev",
        email = "joao@email.com",
        monthlyIncome = 5000.0
    )

    private fun buildRequest() = FinancialEventRequest(
        customerId = 1L,
        type = FinancialEventType.PAYMENT_ON_TIME,
        amount = 500.0,
        description = "Pagamento fatura"
    )

    private fun buildEvent(
        customer: Customer,
        type: FinancialEventType = FinancialEventType.PAYMENT_ON_TIME
    ) = FinancialEvent(
        id = 1L,
        customer = customer,
        type = type,
        amount = 500.0,
        description = "Pagamento fatura"
    )
}
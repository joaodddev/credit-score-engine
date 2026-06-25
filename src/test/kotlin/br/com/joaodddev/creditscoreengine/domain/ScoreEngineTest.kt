package br.com.joaodddev.creditscoreengine.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ScoreEngineTest {

    private val scoreEngine = ScoreEngine()

    @Test
    fun `should return base score when no events`() {
        val score = scoreEngine.calculate(emptyList())
        assertEquals(500, score)
    }

    @Test
    fun `should increase score on payment on time`() {
        val customer = buildCustomer()
        val events = listOf(
            buildEvent(customer, FinancialEventType.PAYMENT_ON_TIME)
        )
        val score = scoreEngine.calculate(events)
        assertEquals(550, score)
    }

    @Test
    fun `should decrease score on payment late`() {
        val customer = buildCustomer()
        val events = listOf(
            buildEvent(customer, FinancialEventType.PAYMENT_LATE)
        )
        val score = scoreEngine.calculate(events)
        assertEquals(420, score)
    }

    @Test
    fun `should decrease score heavily on debt defaulted`() {
        val customer = buildCustomer()
        val events = listOf(
            buildEvent(customer, FinancialEventType.DEBT_DEFAULTED)
        )
        val score = scoreEngine.calculate(events)
        assertEquals(350, score)
    }

    @Test
    fun `should not go below zero`() {
        val customer = buildCustomer()
        val events = (1..20).map {
            buildEvent(customer, FinancialEventType.DEBT_DEFAULTED)
        }
        val score = scoreEngine.calculate(events)
        assertEquals(0, score)
    }

    @Test
    fun `should not exceed 1000`() {
        val customer = buildCustomer()
        val events = (1..20).map {
            buildEvent(customer, FinancialEventType.PAYMENT_ON_TIME)
        }
        val score = scoreEngine.calculate(events)
        assertEquals(1000, score)
    }

    @Test
    fun `should classify score as VERY_LOW`() {
        assertEquals(ScoreClassification.VERY_LOW, scoreEngine.classify(150))
    }

    @Test
    fun `should classify score as LOW`() {
        assertEquals(ScoreClassification.LOW, scoreEngine.classify(400))
    }

    @Test
    fun `should classify score as MEDIUM`() {
        assertEquals(ScoreClassification.MEDIUM, scoreEngine.classify(600))
    }

    @Test
    fun `should classify score as HIGH`() {
        assertEquals(ScoreClassification.HIGH, scoreEngine.classify(800))
    }

    @Test
    fun `should classify score as VERY_HIGH`() {
        assertEquals(ScoreClassification.VERY_HIGH, scoreEngine.classify(950))
    }

    private fun buildCustomer() = Customer(
        id = 1L,
        cpf = "12345678901",
        name = "João Dev",
        email = "joao@email.com",
        monthlyIncome = 5000.0
    )

    private fun buildEvent(customer: Customer, type: FinancialEventType) = FinancialEvent(
        id = 1L,
        customer = customer,
        type = type,
        amount = 100.0,
        description = "Test event"
    )
}
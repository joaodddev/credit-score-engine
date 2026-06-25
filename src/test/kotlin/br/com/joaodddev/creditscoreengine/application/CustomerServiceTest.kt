package br.com.joaodddev.creditscoreengine.application

import br.com.joaodddev.creditscoreengine.domain.Customer
import br.com.joaodddev.creditscoreengine.infrastructure.CustomerRepository
import br.com.joaodddev.creditscoreengine.web.dto.CustomerRequest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class CustomerServiceTest {

    private val customerRepository = mockk<CustomerRepository>()
    private val customerService = CustomerService(customerRepository)

    @Test
    fun `should create customer successfully`() {
        val request = buildRequest()

        every { customerRepository.existsByCpf(request.cpf) } returns false
        every { customerRepository.existsByEmail(request.email) } returns false
        every { customerRepository.save(any()) } returns buildCustomer()

        val response = customerService.create(request)

        assertEquals("12345678901", response.cpf)
        assertEquals("João Dev", response.name)
        verify(exactly = 1) { customerRepository.save(any()) }
    }

    @Test
    fun `should throw when cpf already registered`() {
        val request = buildRequest()

        every { customerRepository.existsByCpf(request.cpf) } returns true

        assertThrows<IllegalArgumentException> {
            customerService.create(request)
        }
        verify(exactly = 0) { customerRepository.save(any()) }
    }

    @Test
    fun `should throw when email already registered`() {
        val request = buildRequest()

        every { customerRepository.existsByCpf(request.cpf) } returns false
        every { customerRepository.existsByEmail(request.email) } returns true

        assertThrows<IllegalArgumentException> {
            customerService.create(request)
        }
        verify(exactly = 0) { customerRepository.save(any()) }
    }

    @Test
    fun `should find customer by id`() {
        every { customerRepository.findById(1L) } returns Optional.of(buildCustomer())

        val response = customerService.findById(1L)

        assertEquals(1L, response.id)
        assertEquals("João Dev", response.name)
    }

    @Test
    fun `should throw when customer not found by id`() {
        every { customerRepository.findById(99L) } returns Optional.empty()

        assertThrows<NoSuchElementException> {
            customerService.findById(99L)
        }
    }

    @Test
    fun `should return all customers`() {
        every { customerRepository.findAll() } returns listOf(buildCustomer(), buildCustomer())

        val result = customerService.findAll()

        assertEquals(2, result.size)
    }

    @Test
    fun `should delete customer successfully`() {
        every { customerRepository.existsById(1L) } returns true
        every { customerRepository.deleteById(1L) } returns Unit

        assertDoesNotThrow { customerService.delete(1L) }
        verify(exactly = 1) { customerRepository.deleteById(1L) }
    }

    @Test
    fun `should throw when deleting non-existent customer`() {
        every { customerRepository.existsById(99L) } returns false

        assertThrows<NoSuchElementException> {
            customerService.delete(99L)
        }
    }

    private fun buildRequest() = CustomerRequest(
        cpf = "12345678901",
        name = "João Dev",
        email = "joao@email.com",
        monthlyIncome = 5000.0
    )

    private fun buildCustomer() = Customer(
        id = 1L,
        cpf = "12345678901",
        name = "João Dev",
        email = "joao@email.com",
        monthlyIncome = 5000.0
    )
}
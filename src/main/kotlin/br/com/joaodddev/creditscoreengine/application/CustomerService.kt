package br.com.joaodddev.creditscoreengine.application

import br.com.joaodddev.creditscoreengine.domain.Customer
import br.com.joaodddev.creditscoreengine.infrastructure.CustomerRepository
import br.com.joaodddev.creditscoreengine.web.dto.CustomerRequest
import br.com.joaodddev.creditscoreengine.web.dto.CustomerResponse
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val customerRepository: CustomerRepository
) {

    fun create(request: CustomerRequest): CustomerResponse {
        if (customerRepository.existsByCpf(request.cpf))
            throw IllegalArgumentException("CPF already registered")

        if (customerRepository.existsByEmail(request.email))
            throw IllegalArgumentException("Email already registered")

        val customer = Customer(
            cpf = request.cpf,
            name = request.name,
            email = request.email,
            monthlyIncome = request.monthlyIncome
        )

        return CustomerResponse.from(customerRepository.save(customer))
    }

    fun findById(id: Long): CustomerResponse {
        val customer = customerRepository.findById(id)
            .orElseThrow { NoSuchElementException("Customer not found with id: $id") }
        return CustomerResponse.from(customer)
    }

    fun findAll(): List<CustomerResponse> =
        customerRepository.findAll().map { CustomerResponse.from(it) }

    fun update(id: Long, request: CustomerRequest): CustomerResponse {
        val customer = customerRepository.findById(id)
            .orElseThrow { NoSuchElementException("Customer not found with id: $id") }

        val updated = Customer(
            id = customer.id,
            cpf = customer.cpf,
            name = request.name,
            email = request.email,
            monthlyIncome = request.monthlyIncome,
            createdAt = customer.createdAt
        )

        return CustomerResponse.from(customerRepository.save(updated))
    }

    fun delete(id: Long) {
        if (!customerRepository.existsById(id))
            throw NoSuchElementException("Customer not found with id: $id")
        customerRepository.deleteById(id)
    }
}
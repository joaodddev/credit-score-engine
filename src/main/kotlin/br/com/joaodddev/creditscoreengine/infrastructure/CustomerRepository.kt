package br.com.joaodddev.creditscoreengine.infrastructure

import br.com.joaodddev.creditscoreengine.domain.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : JpaRepository<Customer, Long> {
    fun findByCpf(cpf: String): Customer?
    fun findByEmail(email: String): Customer?
    fun existsByCpf(cpf: String): Boolean
    fun existsByEmail(email: String): Boolean
}
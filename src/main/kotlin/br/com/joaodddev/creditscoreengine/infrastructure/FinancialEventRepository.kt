package br.com.joaodddev.creditscoreengine.infrastructure

import br.com.joaodddev.creditscoreengine.domain.FinancialEvent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FinancialEventRepository : JpaRepository<FinancialEvent, Long> {
    fun findAllByCustomerId(customerId: Long): List<FinancialEvent>
}
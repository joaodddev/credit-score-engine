package br.com.joaodddev.creditscoreengine.web.controller

import br.com.joaodddev.creditscoreengine.application.FinancialEventService
import br.com.joaodddev.creditscoreengine.web.dto.FinancialEventRequest
import br.com.joaodddev.creditscoreengine.web.dto.FinancialEventResponse
import br.com.joaodddev.creditscoreengine.web.dto.ScoreResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class FinancialEventController(
    private val financialEventService: FinancialEventService
) {

    @PostMapping("/financial-events")
    fun create(@RequestBody @Valid request: FinancialEventRequest): ResponseEntity<FinancialEventResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(financialEventService.create(request))

    @GetMapping("/customers/{customerId}/financial-events")
    fun findAllByCustomer(@PathVariable customerId: Long): ResponseEntity<List<FinancialEventResponse>> =
        ResponseEntity.ok(financialEventService.findAllByCustomer(customerId))

    @GetMapping("/customers/{customerId}/score")
    fun calculateScore(@PathVariable customerId: Long): ResponseEntity<ScoreResponse> =
        ResponseEntity.ok(financialEventService.calculateScore(customerId))
}
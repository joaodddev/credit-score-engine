package br.com.joaodddev.creditscoreengine.web.controller

import br.com.joaodddev.creditscoreengine.application.FinancialEventService
import br.com.joaodddev.creditscoreengine.web.dto.FinancialEventRequest
import br.com.joaodddev.creditscoreengine.web.dto.FinancialEventResponse
import br.com.joaodddev.creditscoreengine.web.dto.ScoreResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Financial Events & Score", description = "Eventos financeiros e cálculo de score")
@SecurityRequirement(name = "Bearer Authentication")
class FinancialEventController(
    private val financialEventService: FinancialEventService
) {

    @PostMapping("/financial-events")
    @Operation(summary = "Registrar evento financeiro")
    fun create(@RequestBody @Valid request: FinancialEventRequest): ResponseEntity<FinancialEventResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(financialEventService.create(request))

    @GetMapping("/customers/{customerId}/financial-events")
    @Operation(summary = "Listar eventos financeiros do cliente")
    fun findAllByCustomer(@PathVariable customerId: Long): ResponseEntity<List<FinancialEventResponse>> =
        ResponseEntity.ok(financialEventService.findAllByCustomer(customerId))

    @GetMapping("/customers/{customerId}/score")
    @Operation(summary = "Calcular score de crédito do cliente")
    fun calculateScore(@PathVariable customerId: Long): ResponseEntity<ScoreResponse> =
        ResponseEntity.ok(financialEventService.calculateScore(customerId))
}
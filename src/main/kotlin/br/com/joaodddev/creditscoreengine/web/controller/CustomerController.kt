package br.com.joaodddev.creditscoreengine.web.controller

import br.com.joaodddev.creditscoreengine.application.CustomerService
import br.com.joaodddev.creditscoreengine.web.dto.CustomerRequest
import br.com.joaodddev.creditscoreengine.web.dto.CustomerResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/customers")
@Tag(name = "Customers", description = "Gerenciamento de clientes")
@SecurityRequirement(name = "Bearer Authentication")
class CustomerController(
    private val customerService: CustomerService
) {

    @PostMapping
    @Operation(summary = "Criar novo cliente")
    fun create(@RequestBody @Valid request: CustomerRequest): ResponseEntity<CustomerResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(customerService.create(request))

    @GetMapping
    @Operation(summary = "Listar todos os clientes")
    fun findAll(): ResponseEntity<List<CustomerResponse>> =
        ResponseEntity.ok(customerService.findAll())

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID")
    fun findById(@PathVariable id: Long): ResponseEntity<CustomerResponse> =
        ResponseEntity.ok(customerService.findById(id))

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente")
    fun update(
        @PathVariable id: Long,
        @RequestBody @Valid request: CustomerRequest
    ): ResponseEntity<CustomerResponse> =
        ResponseEntity.ok(customerService.update(id, request))

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover cliente")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        customerService.delete(id)
        return ResponseEntity.noContent().build()
    }
}
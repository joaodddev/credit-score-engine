package br.com.joaodddev.creditscoreengine.web.controller

import br.com.joaodddev.creditscoreengine.application.CustomerService
import br.com.joaodddev.creditscoreengine.web.dto.CustomerRequest
import br.com.joaodddev.creditscoreengine.web.dto.CustomerResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/customers")
class CustomerController(
    private val customerService: CustomerService
) {

    @PostMapping
    fun create(@RequestBody @Valid request: CustomerRequest): ResponseEntity<CustomerResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(customerService.create(request))

    @GetMapping
    fun findAll(): ResponseEntity<List<CustomerResponse>> =
        ResponseEntity.ok(customerService.findAll())

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<CustomerResponse> =
        ResponseEntity.ok(customerService.findById(id))

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody @Valid request: CustomerRequest
    ): ResponseEntity<CustomerResponse> =
        ResponseEntity.ok(customerService.update(id, request))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        customerService.delete(id)
        return ResponseEntity.noContent().build()
    }
}
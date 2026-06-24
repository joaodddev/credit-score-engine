package br.com.joaodddev.creditscoreengine.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "customers")
class Customer(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true, length = 11)
    val cpf: String,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val monthlyIncome: Double,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)
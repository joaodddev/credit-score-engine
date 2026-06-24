package br.com.joaodddev.creditscoreengine.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "financial_events")
class FinancialEvent(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    val customer: Customer,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val type: FinancialEventType,

    @Column(nullable = false)
    val amount: Double,

    @Column(nullable = false)
    val description: String,

    @Column(nullable = false)
    val occurredAt: LocalDateTime = LocalDateTime.now()
)
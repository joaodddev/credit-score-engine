package br.com.joaodddev.creditscoreengine.domain

enum class FinancialEventType {
    PAYMENT_ON_TIME,
    PAYMENT_LATE,
    DEBT_NEGOTIATED,
    DEBT_DEFAULTED,
    CREDIT_INQUIRY
}
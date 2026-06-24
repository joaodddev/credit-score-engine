package br.com.joaodddev.creditscoreengine.domain

import org.springframework.stereotype.Component

@Component
class ScoreEngine {

    companion object {
        const val BASE_SCORE = 500.0
        const val MAX_SCORE = 1000.0
        const val MIN_SCORE = 0.0
    }

    fun calculate(events: List<FinancialEvent>): Int {
        if (events.isEmpty()) return BASE_SCORE.toInt()

        val score = events.fold(BASE_SCORE) { acc, event ->
            acc + weightOf(event)
        }

        return score.coerceIn(MIN_SCORE, MAX_SCORE).toInt()
    }

    fun classify(score: Int): ScoreClassification = when (score) {
        in 0..300 -> ScoreClassification.VERY_LOW
        in 301..500 -> ScoreClassification.LOW
        in 501..700 -> ScoreClassification.MEDIUM
        in 701..900 -> ScoreClassification.HIGH
        else -> ScoreClassification.VERY_HIGH
    }

    private fun weightOf(event: FinancialEvent): Double = when (event.type) {
        FinancialEventType.PAYMENT_ON_TIME -> +50.0
        FinancialEventType.PAYMENT_LATE -> -80.0
        FinancialEventType.DEBT_NEGOTIATED -> -30.0
        FinancialEventType.DEBT_DEFAULTED -> -150.0
        FinancialEventType.CREDIT_INQUIRY -> -10.0
    }
}
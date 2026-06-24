package br.com.joaodddev.creditscoreengine.web.dto

import br.com.joaodddev.creditscoreengine.domain.ScoreClassification

data class ScoreResponse(
    val customerId: Long,
    val customerName: String,
    val score: Int,
    val classification: ScoreClassification,
    val totalEvents: Int
)
package com.jefisu.portlens.designsystem.components.card.model

import com.jefisu.portlens.designsystem.components.common.model.SemanticTone

data class MiniExemptionCardUi(
    val monthLabel: String,
    val soldAmountLabel: String,
    val usedRatio: Float,
    val usedRatioLabel: String,
    val remainingLabel: String,
    val tone: SemanticTone,
)

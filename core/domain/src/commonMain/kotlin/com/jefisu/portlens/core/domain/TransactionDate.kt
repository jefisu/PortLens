package com.jefisu.portlens.core.domain

data class TransactionDate(val year: Int, val month: Int, val day: Int) {
    init {
        require(month in 1..12) { "month must be in 1..12" }
        require(day in 1..31) { "day must be in 1..31" }
    }
}

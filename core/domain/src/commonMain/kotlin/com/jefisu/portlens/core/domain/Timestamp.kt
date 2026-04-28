package com.jefisu.portlens.core.domain

data class Timestamp(val year: Int, val month: Int, val day: Int, val hour: Int, val minute: Int) {
    init {
        require(month in 1..12) { "month must be in 1..12" }
        require(day in 1..31) { "day must be in 1..31" }
        require(hour in 0..23) { "hour must be in 0..23" }
        require(minute in 0..59) { "minute must be in 0..59" }
    }
}

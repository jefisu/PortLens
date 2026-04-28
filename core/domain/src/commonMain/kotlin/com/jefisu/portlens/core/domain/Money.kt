package com.jefisu.portlens.core.domain

data class Money(val cents: Long) : Comparable<Money> {
    operator fun plus(other: Money): Money = Money(cents = cents + other.cents)

    operator fun minus(other: Money): Money = Money(cents = cents - other.cents)

    fun isPositive(): Boolean = cents > 0

    fun isNegative(): Boolean = cents < 0

    override fun compareTo(other: Money): Int = cents.compareTo(other.cents)

    companion object {
        val Zero = Money(cents = 0)
    }
}

package com.sd.temaAdevarata.pojo

data class Membru(
    var id: Int = 0,
    var firstName: String = "",
    var amount: Array<Int> = arrayOf(0)
)
{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Membru

        if (id != other.id) return false
        if (firstName != other.firstName) return false
        if (!amount.contentEquals(other.amount)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + firstName.hashCode()
        result = 31 * result + amount.contentHashCode()
        return result
    }
}


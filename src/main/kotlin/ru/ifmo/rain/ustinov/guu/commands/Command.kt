package ru.ifmo.rain.ustinov.guu.commands

abstract class Command(private val str: String, private val line: Int) {

    override fun toString(): String {
        return "{$str}: line [$line]"
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }
        if (other is Command) {
            return other.toString() == toString()
        }
        return false
    }

    override fun hashCode(): Int {
        return toString().hashCode()
    }
}

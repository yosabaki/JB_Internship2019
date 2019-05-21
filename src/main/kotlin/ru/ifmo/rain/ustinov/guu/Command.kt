package ru.ifmo.rain.ustinov.guu

import java.util.*

class Command(val name: String, val type: CommandType, private val line: Int, val value: Int) {

    val commands: MutableList<Command>?

    init {
        if (isSub()) {
            commands = ArrayList()
        } else {
            commands = null
        }
    }

    fun isSub(): Boolean {
        return type == CommandType.SUB
    }

    fun add(cmd: Command) {
        assert(isSub())
        commands!!.add(cmd)
    }

    override fun toString(): String {
        return "${type.toString().toLowerCase()} $name ${if (type == CommandType.SET) value.toString() else ""}: line [$line]"
    }
}

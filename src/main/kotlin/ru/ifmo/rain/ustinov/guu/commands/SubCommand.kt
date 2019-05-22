package ru.ifmo.rain.ustinov.guu.commands

import java.util.*

class SubCommand(val name: String, line: Int) : Command("sub $name", line) {

    val commands: MutableList<Command>? = LinkedList()


    fun add(cmd: Command) {
        commands!!.add(cmd)
    }

    override fun equals(other: Any?): Boolean {
        if (other is SubCommand) {
            return commands == other.commands
        }
        return false
    }

    override fun hashCode(): Int {
        return Objects.hash(super.hashCode(), commands)
    }
}

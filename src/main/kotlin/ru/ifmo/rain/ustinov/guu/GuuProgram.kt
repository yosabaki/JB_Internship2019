package ru.ifmo.rain.ustinov.guu

import ru.ifmo.rain.ustinov.guu.commands.*
import ru.ifmo.rain.ustinov.guu.GuuProgram as GuuProgram


class GuuProgram(val subs: HashMap<String, SubCommand>) {
    val main: SubCommand

    init {
        if (!subs.contains("main")) {
            throw GuuParserException("Can't find subprogram \"main\"")
        }
        main = subs["main"]!!
        checkRefs(main, HashSet(), HashSet())
    }

    private fun checkRefs(currCommand: SubCommand, was: HashSet<String>, vars: HashSet<String>) {
        was.add(currCommand.name)
        for (cmd in currCommand.commands!!) {
            when (cmd) {
                is SetCommand -> vars.add(cmd.name)
                is PrintCommand ->
                    if (!vars.contains(cmd.name)) {
                        throw GuuParserException("Variable \"${cmd.name}\" isn't declared: {$cmd}")
                    }
                is CallCommand ->
                    if (!subs.contains(cmd.name)) {
                        throw GuuParserException("Subprogram \"${cmd.name}\" doesn't exist. {$cmd}")
                    } else {
                        if (!was.contains(cmd.name)) {
                            checkRefs(subs[cmd.name]!!, was, vars)
                        }
                    }
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }
        if (other is GuuProgram) {
            return subs == other.subs
        }
        return false
    }

    override fun hashCode(): Int {
        return subs.hashCode()
    }
}

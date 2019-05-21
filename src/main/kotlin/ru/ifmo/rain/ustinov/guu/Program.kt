package ru.ifmo.rain.ustinov.guu

import ru.ifmo.rain.ustinov.guu.CommandType.*


class Program(val subs: HashMap<String, Command>) {
    val main: Command

    init {
        if (!subs.contains("main")) {
            throw GuuParserException("Can't find subprogram \"main\"")
        }
        main = subs["main"]!!
        checkRefs(main, HashSet(), HashSet())
    }

    private fun checkRefs(currCommand: Command, was: HashSet<String>, vars: HashSet<String>) {
        was.add(currCommand.name)
        for (cmd in currCommand.commands!!) {
            if (cmd.type == SET) {
                vars.add(cmd.name)
            } else if (cmd.type == PRINT) {
                if (!vars.contains(cmd.name)) {
                    throw GuuParserException("Variable \"${cmd.name}\" isn't declared: {$cmd}")
                }
            } else if (cmd.type == CALL) {
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
}

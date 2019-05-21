package ru.ifmo.rain.ustinov.guu


class GuuInterpreter(private val program: Program, private var debug: Boolean) {

    private companion object {
        private const val MAX_STACK_SIZE = 2048
    }

    private var exit = false
    private val vars = HashMap<String, Int>()

    private val trace = ArrayList<Pair<String, Command>>()

    private fun showStackTrace() {
        println("Stack size: ${trace.size}")
        for (line in trace) {
            println(
                """${line.first}:
                |   ${line.second}""".trimMargin()
            )
        }
    }

    private fun showVars() {
        if (vars.isEmpty()) {
            println("There are no declared variables")
        } else {
            println("Declared variables:")
        }
        for (variable in vars) {
            println("${variable.key}: ${variable.value}")
        }
    }

    private fun showUsage() {
        println(
            """Usage:
            |continue       - Continue execution without debugging
            |exit           - Stop execution of program
            |i              - Step into subprogram
            |o              - Step over subprogram
            |trace          - Print stacktrace of current state
            |vars           - Print values of declared variables in current state
        """.trimMargin()
        )
    }


    private fun readCommand(): Boolean {
        var stepInto = false
        var flag = true
        while (flag) {
            when (readLine()) {
                "continue" -> {
                    debug = false
                    flag = false
                }
                "exit" -> {
                    exit = true
                    flag = false
                }
                "i" -> {
                    stepInto = true
                    flag = false
                }
                "o" -> {
                    stepInto = false
                    flag = false
                }
                "trace" -> {
                    showStackTrace()
                }
                "vars" -> {
                    showVars()
                }
                else -> showUsage()
            }
        }
        return stepInto
    }

    private fun runSub(command: Command, into: Boolean) {
        trace.add(command.name to command)
        if (trace.size >= MAX_STACK_SIZE) {
            throw GuuInterpretException("Stack overflow", trace)
        }
        var stepInto = false

        for (cmd in command.commands!!) {
            if (exit) return
            trace[trace.size - 1] = command.name to cmd
            if (into && debug) {
                println(command.name + ":")
                println(trace[trace.size - 1].second)
                stepInto = readCommand()
            }
            when (cmd.type) {
                CommandType.CALL -> runSub(program.subs[cmd.name]!!, stepInto)
                CommandType.SET -> vars[cmd.name] = cmd.value
                CommandType.PRINT -> println(vars[cmd.name])
                else -> return // ??
            }
        }
        trace.removeAt(trace.size - 1)
    }

    fun run() {
        runSub(program.main, debug)
    }
}

class GuuInterpretException(s: String, private val trace: ArrayList<Pair<String, Command>>) : Throwable(s) {
    fun printTrace() {
        for (i in 0..Integer.min(29, trace.size)) {
            System.err.println(
                """${trace[i].first}:
                    |   ${trace[i].second}""".trimMargin()
            )
        }
        if (trace.size > 30) {
            System.err.println("...")
        }
    }
}

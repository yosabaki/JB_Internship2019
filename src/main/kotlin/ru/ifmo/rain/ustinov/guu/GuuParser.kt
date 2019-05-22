package ru.ifmo.rain.ustinov.guu

import ru.ifmo.rain.ustinov.guu.CommandType.*
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.lang.NumberFormatException
import java.nio.file.Files
import java.nio.file.InvalidPathException
import java.nio.file.Path
import java.nio.file.Paths


enum class CommandType(val size: Int) { SUB(2), SET(3), CALL(2), PRINT(2) }

class GuuParser {

    private val subs = HashMap<String, Command>()

    private var line = 0

    private fun getToken(str: String): CommandType {
        return when (str) {
            "sub" -> SUB
            "set" -> SET
            "call" -> CALL
            "print" -> PRINT
            else -> throw GuuParserException("Unexpected token at {$str}: line $line")
        }
    }

    private fun parseCommand(str: String): Command? {
        val tokens = str.split(Regex("\\s+"))
        if (tokens.size == 1 && tokens[0] == "") {
            return null
        }
        val token = getToken(tokens[0])
        if (tokens.size != token.size) {
            throw GuuParserException("Unexpected number of tokens at {$str}: line $line")
        }
        var value = 0
        if (tokens.size == 3) {
            try {
                value = Integer.parseInt(tokens[2])
            } catch (e: NumberFormatException) {
                throw GuuParserException("Invalid value at {$str}: line [$line]", e)
            }
        }
        return Command(tokens[1], token, line, value)
    }

    private fun openBufferedReader(path: Path): BufferedReader {
        return try {
            Files.newBufferedReader(path)
        } catch (e: FileNotFoundException) {
            throw GuuParserException("Can't find input file: " + e.message, e)
        } catch (e: SecurityException) {
            throw GuuParserException("Permission denied to input file" + e.message, e)
        }
    }

    fun parse(path: Path): Program? {
        val br = openBufferedReader(path)
        val result: Program
        try {
            var currSub: Command? = null
            while (true) {
                line++
                val str = br.readLine() ?: break
                val cmd = parseCommand(str.trim()) ?: continue
                if (cmd.isSub()) {
                    if (subs.contains(cmd.name)) {
                        throw GuuParserException("Duplicate name of subprogram at {${str.trim()}}: line [$line] \n and at {${subs[cmd.name]!!}}")
                    }
                    subs[cmd.name] = cmd
                    currSub = cmd
                } else {
                    currSub!!.add(cmd)
                }
            }
            result = Program(subs)
        } catch (e: IOException) {
            throw GuuParserException("Can't read from source file: " + e.message, e)
        } finally {
            br.close()
        }
        return result
    }
}

class GuuParserException(message: String?, cause: Throwable?) : Throwable(message, cause) {
    constructor(message: String?) : this(message, null)
}

fun printUsage() {
    System.err.println(
        """Usage:  <file_path> [-debug]
        |   file_path - path to file with guu program
        |   -debug - Start step-by-step debugging
    """.trimMargin()
    )
}

fun main(args: Array<String>) {
    if (args.size > 2 || args.isEmpty()) {
        printUsage()
        return
    }
    var debug = false
    if (args.size == 2) {
        if (args[1] == "-debug") {
            debug = true
        } else {
            printUsage()
            return
        }
    }
    try {
        val inputFile = Paths.get(args[0])
        try {
            val program = GuuParser().parse(inputFile) ?: return
            try {
                GuuInterpreter(program, debug).run()
            } catch (e: GuuInterpretException) {
                System.err.println(e.message)
                e.printTrace()
            }
        } catch (e: GuuParserException) {
            System.err.println(e.message)
        }
    } catch (e: InvalidPathException) {
        System.err.println("Invalid path to source file: ${e.message}")
    }
}

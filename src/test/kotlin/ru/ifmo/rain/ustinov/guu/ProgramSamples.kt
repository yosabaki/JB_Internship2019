package ru.ifmo.rain.ustinov.guu

import ru.ifmo.rain.ustinov.guu.commands.*

fun simpleProgram(): String {
    return """sub main
             |   set a 1
             |   print a
             |   call foo
             |
             |sub foo
             |   set a 2
             |   print a""".trimMargin()
}

fun buildGuuProgram(vararg args: SubCommand): GuuProgram {
    val tmp = HashMap<String, SubCommand>()
    for (arg in args) {
        tmp[arg.name] = arg
    }
    return GuuProgram(tmp)
}

fun parsedSimpleProgram(): GuuProgram {
    val main = SubCommand("main", 1)
    main.add(SetCommand("a", 2, 1))
    main.add(PrintCommand("a", 3))
    main.add(CallCommand("foo", 4))
    val foo = SubCommand("foo", 6)
    foo.add(SetCommand("a", 7, 2))
    foo.add(PrintCommand("a", 8))
    return buildGuuProgram(main, foo)
}

fun simpleRecursion(): String {
    return """sub main
        |   call main
    """.trimMargin()
}

fun missingMain(): String {
    return """sub foo
        |   set a 1
        |   print a
    """.trimMargin()
}

fun invalidCall(): String {
    return """sub main
        |   call goo
        |
        |sub foo
        |   set a 0
    """.trimMargin()
}

fun emptyFunctionBody(): String {
    return """sub main"""
}

fun parsedEmptyFunctionProgram(): GuuProgram {
    val main = SubCommand("main", 1)
    return buildGuuProgram(main)
}

fun emptyProgram(): String {
    return ""
}

fun invalidNumber(): String {
    return """sub main
        |   set a a
        |   print a
    """.trimMargin()
}

fun outOfRangeNumber(): String {
    return """sub main
        |   set a 100000000000000000
        |   print a
    """.trimMargin()
}

fun whitespaces(): String {
    return """           sub            main
        |   ${'\t'}${'\t'}  set           a                0
        |   print a
    """.trimMargin()
}

fun parsedWhitespacesProgram(): GuuProgram {
    val main = SubCommand("main", 1)
    main.add(SetCommand("a", 2, 0))
    main.add(PrintCommand("a", 3))
    return buildGuuProgram(main)
}

fun invalidFunction(): String {
    return """sub main
        |   hello world
    """.trimMargin()
}

fun uninitializedVariable(): String {
    return """sub main
        |   print a
    """.trimMargin()
}

fun globalVariableScope(): String {
    return """sub main
        |   call foo
        |   print b
        |
        |sub foo
        |   set b 1
    """.trimMargin()
}

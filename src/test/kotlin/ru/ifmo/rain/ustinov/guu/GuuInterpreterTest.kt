package ru.ifmo.rain.ustinov.guu

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.io.StringReader

class GuuInterpreterTest {

    private fun shouldThrow(s: String) {
        try {
            GuuInterpreter(parse(s), false).run()
        } catch (e: GuuInterpreterException) {
            return
        }
        fail()
    }

    private fun parse(s: String): GuuProgram {
        val b = BufferedReader(StringReader(s))
        return GuuParser().parse(b)!!
    }

    private val outStream = ByteArrayOutputStream()
    private val errStream = ByteArrayOutputStream()

    @Before
    fun setStdStreams() {
        System.setOut(PrintStream(outStream))
        System.setErr(PrintStream(errStream))
    }

    @After
    fun flush() {
        outStream.flush()
        errStream.flush()
    }

    @Test
    fun testSimpleProgram() {
        GuuInterpreter(parse(simpleProgram()), false).run()
        assertEquals(outStream.toString(), "1\n2\n")
    }

    @Test
    fun testSimpleRecursion() {
        shouldThrow(simpleRecursion())
    }

    @Test
    fun testGlobalVariableScope() {
        GuuInterpreter(parse(globalVariableScope()),false).run()
        assertEquals(outStream.toString(), "1\n")
    }

}

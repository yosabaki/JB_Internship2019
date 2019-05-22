package ru.ifmo.rain.ustinov.guu

import org.junit.Test

import org.junit.Assert.*
import java.io.BufferedReader
import java.io.StringReader

class GuuParserTest {

    private fun simpleShouldThrow(s: String) {
        val b = BufferedReader(StringReader(s))
        try {
            GuuParser().parse(b)
        } catch (e: GuuParserException) {
            return
        }
        fail()
    }

    private fun simpleShouldEquals(s: String, other: GuuProgram) {
        val b = BufferedReader(StringReader(s))
        val f = GuuParser().parse(b)
        assertEquals(f, other)
    }

    @Test
    fun simpleParseTest() {
        simpleShouldEquals(simpleProgram(), parsedSimpleProgram())
    }

    @Test
    fun testMissingMain() {
        simpleShouldThrow(missingMain())
    }

    @Test
    fun testInvalidCall() {
        simpleShouldThrow(invalidCall())
    }

    @Test
    fun testWhitespaces() {
        simpleShouldEquals(whitespaces(), parsedWhitespacesProgram())
    }

    @Test
    fun testEmptyBody() {
        simpleShouldEquals(emptyFunctionBody(), parsedEmptyFunctionProgram())
    }

    @Test
    fun testEmptyProgram() {
        simpleShouldThrow(emptyProgram())
    }

    @Test
    fun testInvalidFunction() {
        simpleShouldThrow(invalidFunction())
    }

    @Test
    fun testInvalidNumber() {
        simpleShouldThrow(invalidNumber())
    }

    @Test
    fun testUninitializedVariable() {
        simpleShouldThrow(uninitializedVariable())
    }

    @Test
    fun testOutOfRangeNumber() {
        simpleShouldThrow(outOfRangeNumber())
    }

}

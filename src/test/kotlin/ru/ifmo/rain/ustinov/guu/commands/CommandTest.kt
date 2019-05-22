package ru.ifmo.rain.ustinov.guu.commands

import org.junit.Test

import org.junit.Assert.*

class CommandTest {

    @Test
    fun testToString() {
        assertEquals(CallCommand("a", 1).toString(), "{call a}: line [1]")
        assertEquals(SetCommand("a", 1, 1).toString(), "{set a 1}: line [1]")
        assertEquals(PrintCommand("a", 1).toString(), "{print a}: line [1]")
        assertEquals(SubCommand("a", 1).toString(), "{sub a}: line [1]")
    }

    @Test
    fun testEquals() {
        val c1 = CallCommand("a", 1)
        val c2 = CallCommand("a", 1)
        val c3 = CallCommand("a", 2)
        val c4 = CallCommand("b", 1)
        assertEquals(c1, c2)
        assertNotEquals(c1, c3)
        assertNotEquals(c1, c4)
        val p1 = PrintCommand("a", 1)
        val p2 = PrintCommand("a", 1)
        val p3 = PrintCommand("a", 2)
        val p4 = PrintCommand("b", 1)
        assertEquals(p1, p2)
        assertNotEquals(p1, p3)
        assertNotEquals(p1, p4)
        val set1 = SetCommand("a", 1, 1)
        val set2 = SetCommand("a", 1, 1)
        val set3 = SetCommand("a", 2, 1)
        val set4 = SetCommand("b", 1, 1)
        val set5 = SetCommand("a", 1, 2)
        assertEquals(set1, set2)
        assertNotEquals(set1, set3)
        assertNotEquals(set1, set4)
        assertNotEquals(set1, set5)
        val s1 = SubCommand("a", 1)
        val s2 = SubCommand("a", 1)
        assertEquals(s1, s2)
        s1.add(c1)
        s2.add(c2)
        s1.add(p1)
        s2.add(p2)
        assertEquals(s1, s2)
        s1.add(set1)
        assertNotEquals(s1, s2)
        s2.add(set3)
        assertNotEquals(s1, s2)
    }

    @Test
    fun testHashCode() {
        val c1 = CallCommand("a", 1)
        val c2 = CallCommand("a", 1)
        val c3 = CallCommand("a", 2)
        val c4 = CallCommand("b", 1)
        assertEquals(c1.hashCode(), c2.hashCode())
        assertNotEquals(c1.hashCode(), c3.hashCode())
        assertNotEquals(c1.hashCode(), c4.hashCode())
        val p1 = PrintCommand("a", 1)
        val p2 = PrintCommand("a", 1)
        val p3 = PrintCommand("a", 2)
        val p4 = PrintCommand("b", 1)
        assertEquals(p1.hashCode(), p2.hashCode())
        assertNotEquals(p1.hashCode(), p3.hashCode())
        assertNotEquals(p1.hashCode(), p4.hashCode())
        val set1 = SetCommand("a", 1, 1)
        val set2 = SetCommand("a", 1, 1)
        val set3 = SetCommand("a", 2, 1)
        val set4 = SetCommand("b", 1, 1)
        val set5 = SetCommand("a", 1, 2)
        assertEquals(set1.hashCode(), set2.hashCode())
        assertNotEquals(set1.hashCode(), set3.hashCode())
        assertNotEquals(set1.hashCode(), set4.hashCode())
        assertNotEquals(set1.hashCode(), set5.hashCode())
        val s1 = SubCommand("a", 1)
        val s2 = SubCommand("a", 1)
        assertEquals(s1.hashCode(), s2.hashCode())
    }
}

package ru.ifmo.rain.ustinov.guu.commands

class SetCommand(val name: String, line: Int, val value: Int) : Command("set $name $value", line)

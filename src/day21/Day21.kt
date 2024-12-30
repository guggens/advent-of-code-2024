package day21

import readInput
import kotlin.test.Test

class Day21 {

    class NumPad {
        enum class Keys {
            UP, DOWN, LEFT, RIGHT, A;

            override fun toString(): String {
                if (this == Keys.UP) {
                    return "^"
                }
                if (this == Keys.DOWN) {
                    return "v"
                }
                if (this == Keys.LEFT) {
                    return "<"
                }
                if (this == Keys.RIGHT) {
                    return ">"
                }
                return "A"
            }

            companion object {
                fun convert(char: Char) : Keys {
                    if (char == 'v') { return Keys.DOWN }
                    if (char == '^') { return Keys.UP }
                    if (char == '<') { return Keys.LEFT }
                    if (char == '>') { return Keys.RIGHT }
                    return Keys.A
                }
            }
        }

        fun path(from: Keys, to: Keys) : String {
            when (from) {
                Keys.UP -> {
                    when(to) {
                        Keys.DOWN-> return "v"
                        Keys.LEFT -> return "v<"
                        Keys.RIGHT -> return "v>"
                        Keys.UP -> return ""
                        Keys.A -> return ">"
                    }
                }
                Keys.LEFT -> {
                    when(to) {
                        Keys.DOWN-> return ">"
                        Keys.LEFT -> return ""
                        Keys.RIGHT -> return ">>"
                        Keys.UP -> return ">^"
                        Keys.A -> return ">^>"
                    }
                }
                Keys.RIGHT -> {
                    when(to) {
                        Keys.DOWN-> return "<"
                        Keys.LEFT -> return "<<"
                        Keys.RIGHT -> return ""
                        Keys.UP -> return "<^"
                        Keys.A -> return "^"
                    }
                }
                Keys.A -> {
                    when(to) {
                        Keys.DOWN-> return "<v"
                        Keys.LEFT -> return "<v<"
                        Keys.RIGHT -> return "v"
                        Keys.UP -> return "<"
                        Keys.A -> return ""
                    }
                }
                Keys.DOWN -> {
                    when(to) {
                        Keys.DOWN-> return ""
                        Keys.LEFT -> return ">"
                        Keys.RIGHT -> return "<"
                        Keys.UP -> return "^"
                        Keys.A -> return ">^"
                    }
                }
            }
        }

    }



    fun part1(input: List<String>): Int {

        val code1 = "^<<A>A>^^AvvvA"
        val code2 = "^^<A<Av>vA>A"
        val code3 = "^^^<<A>A>AvvvA"
        val code4 = "<^^A^>AvAvvA"
        val code5 = "^^<A^AvvAv>A"

        val translatedBuilder = StringBuilder()
        code1.toCharArray().forEach { char ->
            val key = NumPad.Keys.convert(char)
            translatedBuilder.append(key.toString())

        }




        return 0
    }


    fun part2(input: List<String>): Int {
        return 0
    }

    @Test
    fun runDay() {
        val testInput = listOf("")// readInput("day02")

        println("Part 1: ${part1(testInput)}")
        println("Part 2: ${part2(testInput)}")
    }
}
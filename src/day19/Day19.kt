package day19

import org.intellij.lang.annotations.Pattern
import readInput
import readTest
import kotlin.test.Test

class Day19 {

    val towels = mutableListOf<String>()

    val solvableTowels = mutableListOf<String>()

    fun part1(input: List<String>): Int {
        val iterator = input.iterator()
        towels.addAll(iterator.next().split(",").map { it.trim() })
        iterator.next() // newline

        while (iterator.hasNext()) {
            val order = iterator.next()
            stack(order, order)
            println("order: $order processed")
        }

        return solvableTowels.distinct().size
    }

    fun stack(pattern: String, originalOrder: String) {
        for (i in towels.indices) {
            val towel = towels[i]
//            if (solvableTowels.contains(originalOrder)) {
//                break
//            }
            if (pattern.startsWith(towel, ignoreCase = true)) {
                val remainder = pattern.drop(towel.length)
                println(remainder + "solvable size" + solvableTowels.size)
                if (remainder.isEmpty()) {
                    solvableTowels.add(originalOrder)
                    break
                }
                else stack(remainder, originalOrder)
            }
        }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    @Test
    fun runDay() {
        val testInput = readInput("day19")

        println("Part 1: ${part1(testInput)}")
        println("Part 2: ${part2(testInput)}")
    }
}
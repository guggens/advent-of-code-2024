package day05

import readInput
import readTest
import kotlin.test.Test

class Day05 {

    data class Rule(val left: Int, val right: Int)

    fun part1(input: List<String>): Int {
        var sum = 0

        val rules = mutableListOf<Rule>()
        val iterator = input.iterator()
        var line = iterator.next()
        while (line.isNotEmpty()) {
            val pages = line.split("|")
            rules.add(Rule(pages[0].toInt(), pages[1].toInt()))
            line = iterator.next()
        }

        while (iterator.hasNext()) {
            line = iterator.next()
            val pages = line.split(",").map { it.toInt() }
            val relevantRules = rules.filter { pages.contains(it.left) && pages.contains(it.right) }

            var validLine = true
            relevantRules.forEach {
                if (pages.indexOf(it.left) > pages.indexOf(it.right)) {
                    validLine = false
                }
            }
            if (validLine) {
                sum += pages[(pages.size-1)/2]
            }
        }

        return sum
    }


    fun part2(input: List<String>): Int {
        var sum = 0

        val rules = mutableListOf<Rule>()
        val iterator = input.iterator()
        var line = iterator.next()
        while (line.isNotEmpty()) {
            val pages = line.split("|")
            rules.add(Rule(pages[0].toInt(), pages[1].toInt()))
            line = iterator.next()
        }

        val invalidLines = mutableListOf<String>()
        while (iterator.hasNext()) {
            line = iterator.next()
            val pages = line.split(",").map { it.toInt() }
            val relevantRules = rules.filter { pages.contains(it.left) && pages.contains(it.right) }

            var validLine = true
            relevantRules.forEach {
                if (pages.indexOf(it.left) > pages.indexOf(it.right)) {
                    validLine = false
                }
            }
            if (!validLine) {
                invalidLines.add(line)
            }
        }

        invalidLines.forEach { newLine ->
            var pages = newLine.split(",").map { it.toInt() }.toMutableList()
            val relevantRules = rules.filter { pages.contains(it.left) && pages.contains(it.right) }
            var invalidLine = true
            while (invalidLine == true) {
                invalidLine = false
                for (i in 0 until relevantRules.size) {
                    val rule = relevantRules[i]
                    if (pages.indexOf(rule.left) > pages.indexOf(rule.right)) {
                        val right = pages[pages.indexOf(rule.right)]
                        val leftIndex = pages.indexOf(rule.left)
                        pages[pages.indexOf(rule.right)] = pages[leftIndex]
                        pages[leftIndex] = right
                        invalidLine = true
                        break
                    }
                }
            }
            sum += pages[(pages.size-1)/2]
        }

        return sum
    }

    @Test
    fun runDay() {
        val testInput = readInput("day05")

        println("Part 1: ${part1(testInput)}")
        println("Part 2: ${part2(testInput)}")
    }
}
package day03

import readInput
import readTest
import kotlin.test.Test

class Day03 {

    fun part1(input: List<String>): Int {

        var sum = 0

        input.forEach { line ->

            var remainingLine = line

            do {
                val chars = remainingLine.toCharArray()
                val startIndex = remainingLine.indexOf("mul(") + 4
                var index = startIndex

                var invalidMul = false
                do {
                    val char = chars[index]
                    if (char.isDigit() || char == ',' || char == ')') {
                        index++
                    } else {
                        invalidMul = true
                        break
                    }
                } while (!invalidMul && char != ')')

                val multipicants = remainingLine.subSequence(startIndex, index-1)

                if (!multipicants.contains(',')) {
                    invalidMul = true
                }

                if (!invalidMul) {
                    val leftMultiplier = multipicants.substring(0, multipicants.indexOf(',')).toInt()
                    val rightMultiplier = multipicants.substring(multipicants.indexOf(',')+1).toInt()
                    println("found correct mul: $leftMultiplier, $rightMultiplier")
                    sum += leftMultiplier * rightMultiplier
                }

                remainingLine = remainingLine.substring(index)
            } while (remainingLine.contains("mul("))
        }
        return sum
    }


    fun part2(input: List<String>): Int {
        var sum = 0

        var mulEnabled = true

        input.forEach { line ->

            var remainingLine = line

            do {
                val chars = remainingLine.toCharArray()
                val startIndex = remainingLine.indexOf("mul(") + 4
                if (remainingLine.substring(0, startIndex).contains("do()")) {
                    mulEnabled = true
                }
                if (remainingLine.substring(0, startIndex).contains("don't()")) {
                    mulEnabled = false
                }

                var index = startIndex

                var invalidMul = false
                do {
                    val char = chars[index]
                    if (char.isDigit() || char == ',' || char == ')') {
                        index++
                    } else {
                        invalidMul = true
                        break
                    }
                } while (!invalidMul && char != ')')

                val multipicants = remainingLine.subSequence(startIndex, index-1)

                if (!multipicants.contains(',')) {
                    invalidMul = true
                }

                if (!invalidMul) {
                    val leftMultiplier = multipicants.substring(0, multipicants.indexOf(',')).toInt()
                    val rightMultiplier = multipicants.substring(multipicants.indexOf(',')+1).toInt()
                    println("found correct mul: $leftMultiplier, $rightMultiplier")
                    if (mulEnabled) {
                        sum += leftMultiplier * rightMultiplier
                        println("increased sum to $sum")
                    }

                }

                remainingLine = remainingLine.substring(index)
            } while (remainingLine.contains("mul("))
        }
        return sum
    }

    @Test
    fun runDay() {
        val testInput = readInput("day03")

        println("Part 1: ${part1(testInput)}")
        println("Part 2: ${part2(testInput)}")
    }
}
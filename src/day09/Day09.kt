package day09

import readInput
import readTest
import kotlin.test.Test

class Day09 {

    fun part1(input: List<String>): Long {
        val line = input.first()

        val transformedLine = transformLine(line).toCharArray()
        println(transformedLine)

        var endCounter = transformedLine.size - 1
        var startCounter = 0

        while (startCounter < endCounter) {
            val char = transformedLine[startCounter].toString()
            if (char != ".") {
                startCounter++
                continue
            } else {

                var endChar = transformedLine[endCounter].toString()
                while (endChar == ".") {
                    endCounter--
                    endChar = transformedLine[endCounter].toString()
                }
                transformedLine[startCounter] = transformedLine[endCounter]
                transformedLine[endCounter] = '.'

                startCounter++
                endCounter--

                //println(transformedLine)
            }
        }

        println(transformedLine)

        var sum = 0L
        for (i in 0..transformedLine.size) {
            val char = transformedLine[i].toString()
            if (char == ".") {
                break
            } else {
                sum += (char.toLong() * i)
            }
        }

        return sum
    }

    private fun transformLine(line: String): String {
        var id = 0
        var index = 0
        val result = StringBuilder()

        var useBlock = true
        while (index < line.length) {
            val char = line[index++].toString().toInt()
            if (useBlock) {
                for (i in 0 until char) {
                    result.append(id)
                }
                id++
            } else {
                for (i in 0 until char) {
                    result.append('.')
                }
            }
            useBlock = !useBlock
        }
        return result.toString()
    }


    fun part2(input: List<String>): Int {
        return 0
    }

    @Test
    fun runDay() {
        val testInput = readInput("day09")

        println("Part 1: ${part1(testInput)}")
        println("Part 2: ${part2(testInput)}")
    }
}
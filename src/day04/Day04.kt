package day04

import readInput
import kotlin.test.Test

class Day04 {

    fun part1(input: List<String>): Int {

        val matrix: Array<Array<Char>> = input.map { it.toCharArray().toTypedArray() }.toTypedArray()

        val size = matrix[0].size

        var sum = 0

        //horizontal lines, forward and backward
        for (i in matrix.indices) {
            var line = ""
            for (j in matrix[i].indices) {
                line += matrix[i][j]
            }
            val xmasCount = getXmasCount(line)
            println("line: $line, xmasCount: $xmasCount")
            sum += xmasCount
        }

        //vertical lines, forward and backward - we only change matrix[i][j] to matrix[j][i]
        for (i in matrix.indices) {
            var line = ""
            for (j in matrix[i].indices) {
                line += matrix[j][i]
            }

            val xmasCount = getXmasCount(line)
            println("line: $line, xmasCount: $xmasCount")
            sum += xmasCount
        }

        //diagonal lines, from left to top
        for (i in matrix.indices) {
            var line = ""
            for (j in 0..i) {
                line += matrix[i-j][j]
            }
            var xmasCount = getXmasCount(line)
            println("line: $line, xmasCount: $xmasCount")
            sum += xmasCount
        }

        //diagonal lines, 2nd half
        for (i in size-1 downTo 1) {
            var line = ""
            for (j in size-1 downTo i) {
                line += matrix[i+size-1-j][j]
            }
            var xmasCount = getXmasCount(line)
            println("line: $line, xmasCount: $xmasCount")
            sum += xmasCount
        }

        //diagonal lines, from right to top
        for (i in matrix.indices) {
            var line = ""
            for (j in 0..i) {
                line += matrix[i-j][size-1-j]
            }
            var xmasCount = getXmasCount(line)
            println("line: $line, xmasCount: $xmasCount")
            sum += xmasCount
        }

        //diagonal lines, 2nd half
        for (i in size-2 downTo 0) {
            var line = ""
            var start = size-1
            for (j in i downTo 0) {
                line += matrix[start--][j]
            }
            var xmasCount = getXmasCount(line)
            println("line: $line, xmasCount: $xmasCount")
            sum += xmasCount
        }

        return sum
    }

    private fun getXmasCount(line: String): Int {
        val forwardcount = line.replace("XMAS", "!").count { it == '!' }
        val backwardcount = line.reversed().replace("XMAS", "!").count { it == '!' }
        return forwardcount + backwardcount
    }

    fun part2(input: List<String>): Int {
        val matrix: Array<Array<Char>> = input.map { it.toCharArray().toTypedArray() }.toTypedArray()

        val size = matrix[0].size

        var sum = 0
        for (i in 1..<size-1) {
            for (j in 1..<size-1) {

                val char = matrix[i][j]
                if (char == 'A') {
                    val leftUp = matrix[i-1][j-1]
                    val rightUp = matrix[i-1][j+1]
                    val leftDown = matrix[i+1][j-1]
                    val rightDown = matrix[i+1][j+1]

                    val mas1 = "$leftUp$rightDown"
                    val mas2 = "$rightUp$leftDown"
                    if ((mas1 == "MS" || mas1 == "SM") &&
                        (mas2 == "MS" || mas2 == "SM")) {
                        sum++
                    }
                }
            }
        }

        return sum
    }

    @Test
    fun runDay() {
        val testInput = readInput("day04")

        println("Part 1: ${part1(testInput)}")
        println("Part 2: ${part2(testInput)}")
    }
}
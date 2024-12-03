package day01

import readInput
import kotlin.math.abs
import kotlin.test.Test

class Day01 {

    fun locations(input: List<String>): Pair<MutableList<Int>, MutableList<Int>> {
        val leftLocations = mutableListOf<Int>()
        val rightLocations = mutableListOf<Int>()

        input.forEach { line ->
            val leftNumber = line.subSequence(0, line.indexOf(' ')).trim().toString().toInt()
            val rightNumber = line.subSequence(line.indexOf(' '), line.length).trim().toString().toInt()
            leftLocations.add(leftNumber)
            rightLocations.add(rightNumber)
        }

        leftLocations.sort()
        rightLocations.sort()
        return Pair(leftLocations, rightLocations)
    }

    fun part1(input: List<String>): Int {
        val (leftLocations, rightLocations) = locations(input)

        var sum = 0
        for (i in 0..<leftLocations.size) {
            val diff = leftLocations[i] - rightLocations[i]

            sum += abs(diff)
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        val (leftLocations, rightLocations) = locations(input)

        var sum = 0
        for (i in 0..<leftLocations.size) {
            val leftValue = leftLocations[i]
            sum += leftValue * rightLocations.filter { it == leftValue }.count()
        }

        return sum
    }


    @Test
    fun runDay() {
        val testInput = readInput("day01")

        println("Part 1: ${part1(testInput)}")
        println("Part 2: ${part2(testInput)}")
    }
}



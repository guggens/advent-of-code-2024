package day08

import readInput
import readTest
import transposeMatrix
import javax.swing.text.Position
import kotlin.test.Test

class Day08 {

    data class Position(val x: Int, val y: Int) {
        fun isInBounds(max: Int): Boolean {
            return x >= 0 && y >= 0 && x < max && y < max
        }
    }

    fun part1(input: List<String>): Int {
        val matrix: Array<Array<Char>> = input.map { it.toCharArray().toTypedArray() }.toTypedArray()

        val antennas = mutableMapOf<Char, List<Position>>()

        val antinodes = mutableListOf<Position>()

//        for (i in matrix.indices) {
//            for (j in matrix[i].indices) {
//                val char = matrix[i][j]
//                val position = Position(i, j)
//                var antennaFound = false;
//                antennas.entries.forEach { antenna ->
//                    if (antenna.value.contains(position)) {
//                        print(antenna.key)
//                        antennaFound = true
//                    }
//                }
//                if (!antennaFound && antinodes.contains(position)) {
//                    print("X")
//                } else {
//                    print(char)
//                }
//            }
//            println()
//        }

        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                val char = matrix[i][j]
                if (char != '.') {
                    antennas[char] = antennas.getOrDefault(char, emptyList()) + Position(i, j)
                }
            }
        }
        antennas.forEach {
            println("$it")
        }

        antennas.forEach { antenna ->
            val positions = antenna.value
            positions.forEach { currentPosition ->
                positions.forEach { otherPosition ->
                    if (currentPosition != otherPosition) {
                        val xDiff = currentPosition.x - otherPosition.x
                        val yDiff = currentPosition.y - otherPosition.y
                        val antinode = Position(currentPosition.x - xDiff * 2, currentPosition.y - yDiff * 2)
                        if (!antinodes.contains(antinode) && antinode.x >= 0 && antinode.y >= 0 && antinode.x < matrix.size && antinode.y < matrix.size) {
                            antinodes.add(antinode)
                        }
                    }
                }
            }
        }

        antinodes.forEach {
            println(it)
        }

        return antinodes.size
    }


    fun part2(input: List<String>): Int {
        val matrix: Array<Array<Char>> = input.map { it.toCharArray().toTypedArray() }.toTypedArray()

        val antennas = mutableMapOf<Char, List<Position>>()

        val antinodes = mutableListOf<Position>()

        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                val char = matrix[i][j]
                if (char != '.') {
                    antennas[char] = antennas.getOrDefault(char, emptyList()) + Position(i, j)
                }
            }
        }
        antennas.forEach {
            println("$it")
        }

        antennas.forEach { antenna ->
            val positions = antenna.value
            positions.forEach { currentPosition ->
                positions.forEach { otherPosition ->
                    if (currentPosition != otherPosition) {
                        val xDiff = currentPosition.x - otherPosition.x
                        val yDiff = currentPosition.y - otherPosition.y

                        for (i in 1..matrix.size) {
                            val antinode = Position(currentPosition.x + xDiff * i, currentPosition.y + yDiff * i)
                            val antinode2 = Position(currentPosition.x - xDiff * i, currentPosition.y - yDiff * i)
                            if (!antinodes.contains(antinode) && antinode.isInBounds(matrix.size)) {
                                antinodes.add(antinode)
                            }
                            if (!antinodes.contains(antinode2) && antinode2.isInBounds(matrix.size)) {
                                antinodes.add(antinode2)
                            }
                            if (!antinode.isInBounds(matrix.size) && !antinode2.isInBounds(matrix.size)) {
                                break
                            }
                        }
                    }
                }
            }
        }

        antinodes.forEach {
            println(it)
        }

        return antinodes.size
    }

    @Test
    fun runDay() {
        val testInput = readInput("day08")

        println("Part 1: ${part1(testInput)}")
        println("Part 2: ${part2(testInput)}")
    }
}
package day10

import Direction
import Position
import readInput
import readTest
import kotlin.test.Test

class Day10 {

    data class Trail(
        var currentLevel: Int = 0,
        var currentPosition: Position
    )

    class Trailhead(val start: Position, val matrix: Array<Array<Int>>, val uniquePeaks: Boolean = true) {

        var peaks = mutableListOf<Position>()

        fun calculateScore(): Int {
            val trail = Trail(0, start)
            follow(trail)
            if (uniquePeaks) {
                return peaks.distinct().size
            } else {
                return peaks.size
            }
        }

        private fun follow(trail: Trail) {
            for (direction in Direction.entries) {
                if (this.check(direction, trail.currentPosition, trail.currentLevel)) {
                    val newTrail = Trail(trail.currentLevel + 1, trail.currentPosition.moveInDirection(direction))
                    if (newTrail.currentLevel == 9) {
                        peaks.add(newTrail.currentPosition)
                    } else {
                        follow(newTrail)
                    }
                }
            }
        }

        private fun check(direction: Direction, pos: Position, level: Int): Boolean {
            val newPos = pos.moveInDirection(direction)
            if (!newPos.isInBounds(matrix.size)) {
                return false
            }
            return matrix[newPos.x][newPos.y] == level + 1
        }
    }


    fun part1(input: List<String>): Int {
        val matrix: Array<Array<Int>> =
            input.map { it.toCharArray().map { it.toString().toInt() }.toTypedArray() }.toTypedArray()

        var trailheads: MutableList<Trailhead> = mutableListOf()

        for (i in matrix.indices) {
            for (j in matrix[0].indices) {
                if (matrix[i][j] == 0) {
                    trailheads.add(Trailhead(Position(i, j), matrix))
                }
            }
        }

        var sum = 0
        trailheads.forEach {
            sum += it.calculateScore()
        }

        return sum
    }


    fun part2(input: List<String>): Int {
        val matrix: Array<Array<Int>> =
            input.map { it.toCharArray().map { it.toString().toInt() }.toTypedArray() }.toTypedArray()

        var trailheads: MutableList<Trailhead> = mutableListOf()

        for (i in matrix.indices) {
            for (j in matrix[0].indices) {
                if (matrix[i][j] == 0) {
                    trailheads.add(Trailhead(Position(i, j), matrix, uniquePeaks = false))
                }
            }
        }

        var sum = 0
        trailheads.forEach {
            sum += it.calculateScore()
        }

        return sum
    }

    @Test
    fun runDay() {
        val testInput = readInput("day10")

        println("Part 1: ${part1(testInput)}")
        println("Part 2: ${part2(testInput)}")
    }
}
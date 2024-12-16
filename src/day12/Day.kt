package day12

import Direction
import Position
import readInput
import readTest
import kotlin.test.Test

class Day {

    data class Region(
        val startPosition: Position, val letter: String,
        val positions: MutableList<Position> = mutableListOf(),
        val fences: MutableList<Fence> = mutableListOf()
    ) {

        fun spread(matrix: Array<Array<String>>) {
            positions.add(startPosition)
            findMore(matrix, startPosition)
        }

        private fun findMore(matrix: Array<Array<String>>, pos: Position) {
            for (d in Direction.entries) {
                val newPos = pos.moveInDirection(d)
                if (newPos.isInBounds(matrix.size)) {
                    val letter = matrix[newPos.x][newPos.y]
                    if (letter == this.letter && !positions.contains(newPos)) {
                        positions.add(newPos)
                        findMore(matrix, newPos)
                    }
                }
            }
        }

        fun perimeter(matrix: Array<Array<String>>): Int {
            var perimeter = 0
            positions.forEach {
                for (d in Direction.entries) {
                    val newPos = it.moveInDirection(d)
                    if (!newPos.isInBounds(matrix.size)) {
                        perimeter++
                    } else if (matrix[newPos.x][newPos.y] != this.letter) {
                        perimeter++
                    }
                }
            }
            return perimeter
        }

        fun fences(matrix: Array<Array<String>>): List<Fence> {
            positions.forEach {
                println("Checking position $it")
                for (d in Direction.entries) {
                    val newPos = it.moveInDirection(d)
                    if (!newPos.isInBounds(matrix.size)) {
                        addFence(matrix, it, d)
                    } else if (matrix[newPos.x][newPos.y] != this.letter) {
                        addFence(matrix, it, d)
                    }
                }
            }
            return fences
        }

        fun addFence(matrix: Array<Array<String>>, pos: Position, d: Direction) {
            val fenceDirection = d.turn90Right()
            for (fence in fences) {
                if (fence.direction == fenceDirection) {
                    val movedPos = pos.moveInDirection(fenceDirection)
                    if (fence.b == pos) {
                        fence.b = fence.b.moveInDirection(fenceDirection)
                        println("Fence extended $fence")
                        return
                    }
                    if (fence.a == movedPos) {
                        fence.a = pos
                        println("Fence extended $fence")
                        return
                    }

                }
            }
            val newPos = pos.moveInDirection(fenceDirection)
            val fence = Fence(pos, newPos, fenceDirection)
            fences.add(fence)
            println("Fence created $fence")
        }

    }

    data class Fence(var a: Position, var b: Position, val direction: Direction)


    val regions = mutableListOf<Region>()

    fun part1(input: List<String>): Int {
        val matrix: Array<Array<String>> =
            input.map { it.toCharArray().map { it.toString() }.toTypedArray() }.toTypedArray()

        var position = findNextFreePosition(matrix)
        while (position != null) {
            val region = Region(position, matrix[position.x][position.y])
            regions.add(region)
            region.spread(matrix)
            println(region)
            position = findNextFreePosition(matrix)
        }

        var sum = 0
        regions.forEach {
            sum += it.perimeter(matrix) * it.positions.size
        }

        return sum
    }

    fun findNextFreePosition(matrix: Array<Array<String>>): Position? {
        for (i in matrix.indices) {
            for (j in matrix[0].indices) {
                val pos = Position(i, j)
                var positionCovered = false
                for (it in regions) {
                    if (it.positions.contains(pos)) {
                        positionCovered = true
                        continue
                    }
                }
                if (!positionCovered) {
                    return pos
                }
            }
        }
        return null
    }


    fun part2(input: List<String>): Int {
        val matrix: Array<Array<String>> =
            input.map { it.toCharArray().map { it.toString() }.toTypedArray() }.toTypedArray()

        var position = findNextFreePosition(matrix)
        while (position != null) {
            val region = Region(position, matrix[position.x][position.y])
            regions.add(region)
            region.spread(matrix)
            position = findNextFreePosition(matrix)
        }

        var sum = 0
        regions.forEach {
            println("Region: $it")
            val fences = it.fences(matrix)
            sum += fences.size * it.positions.size
        }

        return sum
    }

    @Test
    fun runDay() {
        val testInput = readTest("day12")

        //println("Part 1: ${part1(testInput)}")
        println("Part 2: ${part2(testInput)}")
    }
}
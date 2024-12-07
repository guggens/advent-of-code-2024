package day06

import readInput
import readTest
import java.awt.Event.DOWN
import java.awt.Event.UP
import java.nio.file.DirectoryNotEmptyException
import kotlin.io.path.*
import kotlin.test.Test

class Day06 {

    var startX: Int? = null
    var startY: Int? = null

    enum class Direction {
        UP, DOWN, LEFT, RIGHT;

        fun turnRight(): Direction {
            return when (this) {
                UP -> RIGHT
                RIGHT -> DOWN
                DOWN -> LEFT
                LEFT -> UP
            }
        }
    }

    data class Memory(val x: Int, val y: Int, val direction: Direction)

    data class Guard(var x: Int, var y: Int, var direction: Direction, var matrix: Array<Array<Char>>) {
        var visitedFields:  Array<Array<Char>> = Array(matrix.size) { Array(matrix[0].size) { ' ' } }
        var wereWasIAlready: MutableList<Memory> = mutableListOf()

        var obstacleX: Int? = null
        var obstacleY: Int? = null

        init {
            visitedFields[x][y] = 'X'
            wereWasIAlready.add(Memory(x, y, direction))
        }

        fun getPositionInFront(): Pair<Int, Int> {
            return when (direction) {
                Direction.UP -> Pair(x, y-1)
                Direction.DOWN -> Pair(x, y+1)
                Direction.LEFT -> Pair(x-1, y)
                Direction.RIGHT -> Pair(x+1, y)
            }
        }

        fun charInFront(): Char {
            try {
                val position = getPositionInFront()
                return matrix[position.first][position.second]
            } catch (e: ArrayIndexOutOfBoundsException) {
                return ' '
            }
        }

        fun moveForward() {
            when (direction) {
                Direction.UP -> y--
                Direction.DOWN -> y++
                Direction.LEFT -> x--
                Direction.RIGHT -> x++
            }
            if (!isOutOfBounds()) {
                visitedFields[x][y] = 'X'
                wereWasIAlready.add(Memory(x, y, direction))
            }
        }

        fun isStuckInLoop(): Boolean {
            val now = Memory(x, y, direction)
            return wereWasIAlready.indexOf(now) < wereWasIAlready.size-1 && wereWasIAlready.indexOf(now) > 0
        }

        fun isOutOfBounds(): Boolean {
            if (x < 0 || y < 0 || x >= matrix[0].size || y >= matrix.size) {
                return true
            }
            return false
        }

        fun getObstaclePosition(): Pair<Int, Int> {
            return Pair(obstacleX!!, obstacleY!!)
        }

        fun turnRight() {
            direction = direction.turnRight()
            wereWasIAlready.add(Memory(x, y, direction))
        }
    }

    fun transposeMatrix(matrix: Array<Array<Char>>): Array<Array<Char>> {
        val transposed = Array(matrix[0].size) { Array(matrix.size) { ' ' } }
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                transposed[j][i] = matrix[i][j]
            }
        }
        return transposed
    }

    fun part1(input: List<String>): Int {
        val matrix: Array<Array<Char>> = transposeMatrix(input.map { it.toCharArray().toTypedArray() }.toTypedArray())

        val guard = initGuard(matrix)
        do {
            val charInFront = guard.charInFront()
            println(charInFront)
            if (charInFront == '#') {
                guard.turnRight()
            }
            guard.moveForward()
            println(guard)
        } while (!guard.isOutOfBounds())

        var sum = 0
        guard.visitedFields.forEach {
            sum += it.count { char -> char == 'X' }
        }
        return sum
    }

    private fun initGuard(matrix: Array<Array<Char>>): Guard {
        var guard: Guard? = null
        if (startX != null && startY != null) {
            guard = Guard(startX!!, startY!!, Direction.UP, matrix)
            return guard
        }
        //find the starting point
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j] == '^') {
                    guard = Guard(i, j, Direction.UP, matrix)
                    break
                }
            }
            if (guard != null) {
                break
            }
        }
        startX = guard!!.x
        startY = guard.y
        return guard
    }


    fun part2(input: List<String>): Int {
        var matrix: Array<Array<Char>> = transposeMatrix(input.map { it.toCharArray().toTypedArray() }.toTypedArray())
        var sum = 0
        var obstaclePositions = mutableListOf<Pair<Int, Int>>()

        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                matrix = transposeMatrix(input.map { it.toCharArray().toTypedArray() }.toTypedArray())
                val guard = initGuard(matrix)

                if (matrix[i][j] == '#' || matrix[i][j] == '^') {
                    continue
                }
                guard.obstacleX = i
                guard.obstacleY = j
                guard.matrix[guard.obstacleX!!][guard.obstacleY!!] = '#'

                do {
                    val charInFront = guard.charInFront()
                    if (charInFront == '#') {
                        guard.direction = guard.direction.turnRight()
                    }
                    guard.moveForward()
                } while (!guard.isOutOfBounds() && !guard.isStuckInLoop())

                if (guard.isStuckInLoop() && !guard.isOutOfBounds()) {
                    val position = guard.getObstaclePosition()
                    if (!obstaclePositions.contains(position)) {
                        obstaclePositions.add(position)
                    }
                    println("guard is stuck! + ${obstaclePositions.size}")
                    //printMatrix(matrix, guard)

                    sum++
                }

            }
        }

        println("POSITIONS: ${obstaclePositions.size}")
        return sum
    }

    private fun printMatrix(matrix: Array<Array<Char>>, guard: Guard) {
        val output = StringBuilder()

        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (guard.obstacleX == j && guard.obstacleY == i) {
                    output.append('O')
                } else if (startX == j && startY == i) {
                    output.append('^')
                } else if (guard.x == j && guard.y == i) {
                    output.append('G')
                } else if (guard.visitedFields[j][i] == 'X') {
                    output.append('X')
                } else {
                    output.append(matrix[j][i])
                }
            }
            output.append("\n")
        }

        val path = Path("src/day06/results/${guard.obstacleX}_${guard.obstacleY}.txt")
        path.writeText(output.toString())
    }

    @OptIn(ExperimentalPathApi::class)
    @Test
    fun runDay() {
//        Path("src/day06/results").deleteRecursively()
//        Path("src/day06/results").createDirectory()

        val testInput = readInput("day06")

        println("Part 1: ${part1(testInput)}")
        println("Part 2: ${part2(testInput)}")
    }
}
package day18

import Direction
import Matrix
import Position
import readInput
import readTest
import kotlin.test.Test

const val SIZE = 70
class Day18 {

    data class Path(
        val score: Int = 0,
        val pos: Position = Position(0, 0),
        val direction: Direction = Direction.RIGHT,
    )

    lateinit var matrix: Matrix

    var cheapestPaths = mutableMapOf<Position, Int>()
    val obstacles = mutableListOf<Position>()
    var solutions = mutableListOf<Path>()

    fun part1(input: List<String>): Int {

        val iterator = input.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            val pos = next.split(",").map { it.toInt() }
            obstacles.add(Position(pos[0], pos[1]))
            if (obstacles.size == 1024) {
                break
            }
        }

        val fixedMatrix = mutableListOf<String>()
        for (i in 0..SIZE) {
            var line = ""
            for (j in 0..SIZE) {
                if (obstacles.contains(Position(i, j))) {
                    line += "#"
                } else {
                    line += "."
                }
            }
            fixedMatrix.add(line)
        }

        matrix = Matrix(fixedMatrix)

        val start = Path(
            pos = Position(0, 0),
            direction = Direction.RIGHT,
        )

        var paths = mutableListOf(start)
        while (true) {
            val newPaths = mutableListOf<Path>()
            paths.forEach {
                val newPathsForPath = walk(it)
                newPathsForPath.forEach { newPath ->
                    val cheapest = cheapestPaths[newPath.pos]
                    if (cheapest == null || newPath.score <= cheapest) {
                        cheapestPaths[newPath.pos] = newPath.score
                        newPaths.add(newPath)
                    }
                }
            }
            paths = newPaths
            if (paths.isEmpty()) {
                break
            }
            //println("paths.size: ${paths.size}, solutions.size = ${solutions.size}")
        }

        return solutions.minOf { it.score }
    }

    fun walk(path: Path) : List<Path>{

        var paths: MutableList<Path> = mutableListOf()

        for (direction in Direction.entries) {
            // we dont want to go back
            if (direction.invert() == path.direction) {
                continue
            }

            val newPos = path.pos.moveInDirection(direction)

            if (cheapestPaths.keys.contains(newPos)) {
                //we have been here already
                continue
            }

            if (!newPos.isInBounds(SIZE+1)) {
                continue
            }
            val newChar = matrix.getChar(newPos)
            if (newChar == '#') {
                //we hit a wall
                continue
            }

            val newPath = Path(
                score = path.score + 1,
                pos = newPos,
                direction = direction
            )

            if (newPos == Position(SIZE,SIZE)) {
                //we found the exit
                solutions.add(newPath)
            } else {
                paths.add(newPath)
            }
        }

        return paths
    }


    fun part2(input: List<String>): String{
        obstacles.clear()

        val iterator = input.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            val pos = next.split(",").map { it.toInt() }
            obstacles.add(Position(pos[0], pos[1]))
            if (obstacles.size == 1024) {
                break
            }
        }

        val fixedMatrix = mutableListOf<String>()
        for (i in 0..SIZE) {
            var line = ""
            for (j in 0..SIZE) {
                if (obstacles.contains(Position(i, j))) {
                    line += "#"
                } else {
                    line += "."
                }
            }
            fixedMatrix.add(line)
        }

        matrix = Matrix(fixedMatrix).also { it.transpose() }

        var obstacleCount = 1024
        while (iterator.hasNext()) {
            val next = iterator.next()
            val pos = next.split(",").map { it.toInt() }
            val position = Position(pos[0], pos[1])
            obstacles.add(position)
            matrix.setChar('#', position)

            solutions = mutableListOf()
            cheapestPaths = mutableMapOf()

            findSolutions()
            if (solutions.isEmpty()) {
                return "${pos[0]},${pos[1]}"
            } else {
                println("found ${solutions.size} solutions for ${obstacleCount++} obstacles ")
            }
        }

        return "NOPE"
    }

    private fun findSolutions() {
        val start = Path(
            pos = Position(0, 0),
            direction = Direction.RIGHT,
        )

        var paths = mutableListOf(start)
        while (true) {
            val newPaths = mutableListOf<Path>()
            paths.forEach {
                val newPathsForPath = walk(it)
                newPathsForPath.forEach { newPath ->
                    val cheapest = cheapestPaths[newPath.pos]
                    if (cheapest == null || newPath.score <= cheapest) {
                        cheapestPaths[newPath.pos] = newPath.score
                        newPaths.add(newPath)
                    }
                }
            }
            paths = newPaths
            if (paths.isEmpty()) {
                break
            }
            //println("paths.size: ${paths.size}, solutions.size = ${solutions.size}")
        }
    }

    @Test
    fun runDay() {
        val testInput = readInput("day18")

        println("Part 1: ${part1(testInput)}")
        println("Part 2: ${part2(testInput)}")
        println(matrix)
    }
}
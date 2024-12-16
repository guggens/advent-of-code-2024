package day16

import Direction
import Matrix
import Position
import readInput
import readTest
import transposeMatrix
import java.nio.file.Files.walk
import kotlin.test.Test

class Day16 {

    data class Path(
        val score: Int = 0,
        val pos: Position = Position(0, 0),
        val direction: Direction = Direction.RIGHT,
        val history: List<Position> = listOf()
    )

    val solutions = mutableListOf<Path>()

    val cheapestPaths = mutableMapOf<Position, Int>()

    lateinit var matrix: Matrix

    fun part1(input: List<String>): Int {
        matrix = Matrix(input)

        val start = Path(pos = matrix.findChar('S'), direction = Direction.RIGHT, history = listOf(matrix.findChar('S')))

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
            println("paths.size: ${paths.size}, path.depth = ${paths[0].history.size}, solutions.size = ${solutions.size}")
        }

        val min = solutions.minOf { it.score }

        val minSolution = solutions.filter { it.score == min }
        println("minimal solutions:" + minSolution.size)

        val positions = mutableListOf<Position>(matrix.findChar('E'))
        minSolution.forEach {
            positions.addAll(it.history)
        }
        println("Part2: distinct positions: " + positions.distinct().size)

        return min
    }

    fun walk(path: Path) : List<Path>{

        var paths: MutableList<Path> = mutableListOf()

        for (direction in Direction.entries) {
            // we dont want to go back
            if (direction.invert() == path.direction) {
                continue
            }

            val newPos = path.pos.moveInDirection(direction)

            if (path.history.contains(newPos)) {
                //we have been here already
                continue
            }

            val newChar = matrix.getChar(newPos)
            if (newChar == '#') {
                //we hit a wall
                continue
            }

            val scoreIncrement = if (direction == path.direction) 1 else 1001
            val newPath = Path(score = path.score+scoreIncrement, pos = newPos, direction = direction, history = path.history + newPos)

            if (newChar == 'E') {
                //we found the exit
                solutions.add(newPath)
            } else {
     //           println("Walking to $newPos, direction $direction")
                paths.add(newPath)
            }
        }

//        if (paths.isEmpty()) {
//            println("No more paths, SACKGASSE")
//        }

        return paths
    }



    fun part2(input: List<String>): Int {
        return 0
    }

    @Test
    fun runDay() {
        val testInput = readInput("day16")

        println("Part 1: ${part1(testInput)}")
        println("Part 2: ${part2(testInput)}")
    }
}
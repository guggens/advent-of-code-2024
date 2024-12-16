package day15

import Position
import println
import readInput
import readTest
import kotlin.test.Test

class Day15 {

    class World() {
        val walls = mutableListOf<Position>()
        val boxes = mutableListOf<Position>()
        var robot = Position(0, 0)
        var sizeX = 0
        var sizeY = 0

        override fun toString(): String {
            val builder = StringBuilder()

            for (y in 0..sizeY) {
                for (x in 0..sizeX) {
                    val pos = Position(x, y)
                    if (walls.contains(pos)) {
                        builder.append("#")
                    } else if (boxes.contains(pos)) {
                        builder.append("O")
                    } else if (robot == pos) {
                        builder.append("@")
                    } else {
                        builder.append(".")
                    }
                }
                builder.append("\n")
            }

            return builder.toString()
        }

        fun moveRobot(direction: Char) {
            var vX = 0
            var vY = 0
            when (direction) {
                '^' -> vY = -1
                'v' -> vY = 1
                '<' -> vX = -1
                '>' -> vX = 1
            }

            var x = robot.x
            var y = robot.y
            val boxesToChange = mutableListOf<Position>()
            while (true) {
                x += vX
                y += vY
                val newPos = Position(x, y)
                if (boxes.contains(newPos)) {
                    boxesToChange.add(newPos)
                    continue
                } else if (walls.contains(newPos)) {
                    break
                } else {
                    //we found a blank space, so we can move robot and all boxes
                    robot = Position(robot.x + vX, robot.y + vY)
                    boxesToChange.forEach {
                        boxes.remove(it)
                        boxes.add(Position(it.x + vX, it.y + vY))
                    }
                    break
                }
            }
        }

    }


    fun parseWorld(input: List<String>): World {
        val world = World()

        val iterator = input.iterator()
        var y = 0
        while (true) {
            val line = iterator.next()
            if (line.isEmpty()) {
                break
            }
            var x = 0
            line.toCharArray().forEach { char ->
                if (char == '#') {
                    world.walls.add(Position(x, y))
                } else if (char == 'O') {
                    world.boxes.add(Position(x, y))
                } else if (char == '@') {
                    world.robot = Position(x, y)
                }

                x++
            }
            world.sizeX = x - 1
            y++

        }
        world.sizeY = y - 1
        return world
    }


    fun part1(input: List<String>): Int {
        val world = parseWorld(input)

        val iterator = input.iterator()
        while (iterator.hasNext()) {
            val line = iterator.next()
            if (line.isEmpty()) {
                break
            }
            if (line.startsWith("#")) {
                continue
            }
        }

        println(world)
        while (iterator.hasNext()) {
            val line = iterator.next()

            line.toCharArray().forEach {
                world.moveRobot(it)
                //println(world)
            }
        }

        var sum = 0
        world.boxes.forEach {
            sum += it.x + (it.y * 100)
        }

        return sum
    }


    fun part2(input: List<String>): Int {
        return 0
    }

    @Test
    fun runDay() {
        val testInput = readInput("day15")

        println("Part 1: ${part1(testInput)}")
        println("Part 2: ${part2(testInput)}")
    }
}
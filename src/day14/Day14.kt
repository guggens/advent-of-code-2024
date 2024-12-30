package day14

import day06cheater.Y24Day06.Pos
import readInput
import readTest
import kotlin.test.Test


class Day14 {

    data class Robot(
        var x: Int = 0,
        var y: Int = 0,
        val vX: Int,
        val vY: Int,
    ) {
        fun move(xSize: Int, ySize: Int) {
            x += vX
//            if (x > xSize) x -= xSize
//            if (x < 0) x += xSize

            y += vY
//            if (y > ySize) y -= ySize
//            if (y < 0) y += ySize
        }

        fun posIn(divX: Int, divY: Int): Pos {
            return Pos((divX + (x % divX)) % divX, (divY + (y % divY)) % divY)
        }
    }

    fun part1(input: List<String>): Int {
        val xSize = 103
        val ySize = 101

        val robots = parseBots(input)

        for (i in 0..7623) {
            robots.forEach { robot -> robot.move(xSize, ySize) }
            println("ROBOTS $i")
            print(ySize, xSize, robots)
        }

        val middleX = (xSize - 1) / 2
        val middleY = (ySize - 1) / 2


        val q1Bots = robots.map { it.posIn(xSize, ySize) }.filter { it.x < middleX && it.y < middleY }.size
        val q2Bots = robots.map { it.posIn(xSize, ySize) }.filter { it.x > middleX && it.y < middleY }.size
        val q3Bots = robots.map { it.posIn(xSize, ySize) }.filter { it.x < middleX && it.y > middleY }.size
        val q4Bots = robots.map { it.posIn(xSize, ySize) }.filter { it.x > middleX && it.y > middleY }.size

        return q1Bots * q2Bots * q3Bots * q4Bots
    }

    private fun print(ySize: Int, xSize: Int, robots: List<Robot>) {
        for (i in 0 until ySize) {
            for (j in 0 until xSize) {
                val size = robots.map { it.posIn(xSize, ySize) }.filter { it.x == j && it.y == i }.size
                if (size == 0) {
                    print('.')
                } else {
                    print(size)
                }
            }
            println()
        }
    }

    private fun parseBots(input: List<String>): List<Robot> {
        return input.map { line ->
            val x = line.substring(2, line.indexOf(",")).toInt()
            val y = line.substring(line.indexOf(",") + 1, line.indexOf("v")).trim().toInt()
            val remainder = line.substring(line.indexOf("v="))
            val vX = remainder.substring(2, remainder.indexOf(",")).toInt()
            val yX = remainder.substring(remainder.indexOf(",") + 1).toInt()
            Robot(x = x, y = y, vX = vX, vY = yX)
        }
    }


    fun part2(input: List<String>): Int {
        return 0
    }

    @Test
    fun runDay() {
        val testInput = readInput("day14")

        println("Part 1: ${part1(testInput)}")
        println("Part 2: ${part2(testInput)}")
    }
}
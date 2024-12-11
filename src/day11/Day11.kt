package day11

import readInput
import readTest
import kotlin.test.Test

class Day11 {

    fun part1(input: List<String>): Long {

        var stones = input[0].split(" ").map { it.toLong() }

        for (i in 0 until 25) {
            stones = blink(stones)
//            println(stones)
            println("Blink $i + stones: ${stones.size}")
        }

        return stones.size.toLong()
    }

    fun blink(stones: List<Long>): List<Long> {
        val newStones = mutableListOf<Long>()
        stones.forEach { stone ->
            if (stone == 0L) {
                newStones.add(1)
            } else if (stone.toString().length % 2 == 0) {
                val left = stone.toString().substring(0, stone.toString().length / 2)
                val right = stone.toString().substring(stone.toString().length / 2)
                newStones.add(left.toLong())
                newStones.add(right.toLong())
            } else {
                newStones.add(stone * 2024)
            }
        }
        return newStones
    }

    fun blinkStorage(stones: Map<Long, Long>): MutableMap<Long, Long> {
        val newStones = mutableMapOf<Long, Long>()
        stones.forEach { entry ->

            // we do know we have entry.value stones

            if (entry.key == 0L) {
                val count = newStones[1] ?: 0
                newStones[1] = count + entry.value
            } else if (entry.key.toString().length % 2 == 0) {
                val stone = entry.key.toString()
                val left = stone.substring(0, stone.length / 2).toLong()
                val right = stone.substring(stone.length / 2).toLong()

                val countLeft = newStones[left] ?: 0
                newStones[left] = countLeft + entry.value

                val countRight = newStones[right] ?: 0
                newStones[right] = countRight + entry.value
            } else {
                val newKey = entry.key * 2024
                val count = newStones[newKey] ?: 0
                newStones[newKey] = count + entry.value
            }


//
//
//            for (i in 0..<entry.value) {
//                if (entry.key == 0L) {
//                    val count = newStones[1] ?: 0
//                    newStones[1] = count + 1
//                } else if (entry.key.toString().length % 2 == 0) {
//                    val stone = entry.key.toString()
//                    val left = stone.substring(0, stone.length / 2).toLong()
//                    val right = stone.substring(stone.length / 2).toLong()
//
//                    val countLeft = newStones[left] ?: 0
//                    newStones[left] = countLeft + 1
//
//                    val countRight = newStones[right] ?: 0
//                    newStones[right] = countRight + 1
//                } else {
//                    val newKey = entry.key * 2024
//                    val count = newStones[newKey] ?: 0
//                    newStones[newKey] = count + 1
//                }
//            }
        }
        return newStones
    }


    var stonesStore: MutableMap<Long, Long> = mutableMapOf()


    fun part2(input: List<String>): Long {
        val stones = input[0].split(" ").map { it.toLong() }

        stones.forEach {
            val count = stonesStore[it] ?: 0
            stonesStore[it] = count + 1
        }

        for (i in 0 until 75) {
            stonesStore = blinkStorage(stonesStore)
            println("Blink $i + stones: ${stonesStore.size}")
        }

        var sum = 0L
        stonesStore.forEach {
            sum += it.value
        }
        return sum
    }

    @Test
    fun runDay() {
        val testInput = readInput("day11")

        println("Part 1: ${part1(testInput)}")
        println("Part 2: ${part2(testInput)}")
    }
}
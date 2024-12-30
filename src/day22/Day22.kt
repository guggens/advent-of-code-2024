package day22

import readInput
import readTest
import kotlin.test.Test

class Day22 {

    class RandomNumber(var seed: Long) {
        fun next(): Long {
            mix(seed * 64)
            prune()
            mix(seed / 32)
            prune()
            mix(seed * 2048)
            prune()
            return seed
        }

        fun mix(number: Long) {
            seed = seed xor number
        }

        fun prune() {
            seed = seed % 16777216L
        }

    }

    data class Sequence(val diff1: Long, val diff2: Long, val diff3: Long, val diff4: Long) {
        fun shift(number: Long): Sequence {
            return Sequence(diff2, diff3, diff4, number)
        }
    }

    val sequenceBananas = mutableMapOf<Sequence, Long>()

    fun part1(input: List<String>): Long {
        var sum = 0L
        input.forEach {
            val num = RandomNumber(it.trim().toLong())
            var result: Long = 0L
            for (i in 1..2000) {
                result = num.next()
            }
            sum+=result
        }

        return sum
    }

    fun diff(num1: Long, num2: Long): Long {
        return num2.toString().last().toString().toLong() -  num1.toString().last().toString().toLong()
    }

    fun part2(input: List<String>): Long {
        var sum = 0L
        input.forEach {
            val seedSequence = mutableListOf<Sequence>()

            val num = RandomNumber(it.trim().toLong())

            val num1 = num.next()
            val num2 = num.next()
            val num3 = num.next()
            val num4 = num.next()

            var sequence = Sequence(
                diff1 = diff(it.toLong(), num1),
                diff2 = diff(num1, num2),
                diff3 = diff(num2, num3),
                diff4 = diff(num3, num4),
            )
            seedSequence.add(sequence)

            var bananas = sequenceBananas.getOrDefault(sequence, 0L)
            sequenceBananas[sequence] = bananas + num4.toString().last().toString().toLong()

            var lastNum = num4

            for (i in 1..2000-4) {
                val number = num.next()
                sequence = sequence.shift(diff(lastNum, number))
                if (seedSequence.contains(sequence)) {
                    continue
                }
                seedSequence.add(sequence)
                lastNum = number
                bananas = sequenceBananas.getOrDefault(sequence, 0L)
                sequenceBananas[sequence] = bananas + number.toString().last().toString().toLong()
            }
        }

        return sequenceBananas.values.max()
    }

    @Test
    fun runDay() {
        val testInput = readInput("day22")

        println("Part 1: ${part1(testInput)}")
        println("Part 2: ${part2(testInput)}")
    }
}
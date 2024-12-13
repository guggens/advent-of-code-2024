package day13

import Position
import readInput
import readTest
import kotlin.test.Test

class Day13 {

    data class Button(val x:Long, val y:Long) {
    }

    data class Price(val x: Long, val y: Long)

    fun part1(input: List<String>): Long {
        var sum = 0L

        val iterator = input.iterator()
        while (iterator.hasNext()) {
            var next = iterator.next()

            var x = next.substring(next.indexOf('+') , next.indexOf(',')).toInt()
            var remainder = next.substring(next.indexOf("$x"))
            var y = remainder.substring(remainder.indexOf("+")).toInt()
            val buttonA = Button(x.toLong(), y.toLong())

            next = iterator.next()
            x = next.substring(next.indexOf('+') , next.indexOf(',')).toInt()
            remainder = next.substring(next.indexOf("$x"))
            y = remainder.substring(remainder.indexOf("+")).toInt()
            val buttonB = Button(x.toLong(), y.toLong())

            next = iterator.next()
            x = next.substring(next.indexOf('=')+1 , next.indexOf(',')).toInt()
            remainder = next.substring(next.indexOf("$x"))
            y = remainder.substring(remainder.indexOf("=")+1).toInt()
            val price = Price(x + 10000000000000L, y + 10000000000000L)
            val costs = calculateMinimalCosts(buttonA, buttonB, price)

            println(buttonA)
            println(buttonB)
            println(price)
            println(costs)
            sum += costs

            if (iterator.hasNext()) {
                next = iterator.next()
            }
        }

        return sum
    }

    private fun calculateMinimalCosts(buttonA: Button, buttonB: Button, price: Price): Long {
        var minimalCosts = 0L

        var i = 0L
        while (true) {
            val aXtimesI = buttonA.x * i
            if (i%1000000000 == 0L) {
                println("a*i: $aXtimesI  -  ${price.x} - minCosts$minimalCosts")
            }
            if (aXtimesI >= price.x) {
                break
            }

            val targetX = price.x - aXtimesI
            if (targetX % buttonB.x == 0L) {

                if (minimalCosts != 0L && i * 3 >= minimalCosts) {
                    break
                }

                val buttonBpresses = targetX / buttonB.x

                if (buttonA.y * i + buttonB.y * buttonBpresses == price.y) {
                    val costs = i * 3 + buttonBpresses
                    if (costs == 0L || costs > minimalCosts) {
                        minimalCosts = costs
                    }
                }
            }

            i++
        }

//        for (i in 0..100) {
//            val targetX = price.x - (buttonA.x * i)
//            if (targetX % buttonB.x.toLong() == 0L) {
//
//                val buttonBpresses = targetX / buttonB.x
//
//                if (buttonA.y * i + buttonB.y * buttonBpresses == price.y) {
//                    val costs = i * 3 + buttonBpresses
//                    if (costs == 0L || costs > minimalCosts) {
//                        minimalCosts = costs
//                    }
//                }
//            }
//        }
        return minimalCosts
    }


    fun part2(input: List<String>): Int {
        return 0
    }

    @Test
    fun runDay() {
        val testInput = readTest("day13")

        println("Part 1: ${part1(testInput)}")
        println("Part 2: ${part2(testInput)}")
    }
}
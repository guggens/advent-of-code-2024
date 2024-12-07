package day07

import readInput
import readTest
import kotlin.test.Test

class Day07 {

    fun part1(input: List<String>): Long {
        var sum = 0L
        input.forEach { line ->
            val parts = line.split(":")
            val correctResult = parts[0]
            val terms = parts[1].split(" ").drop(1).map { it.toLong() }

            val isCorrect = calculateExpression(terms[0], terms[1], terms.drop(2), correctResult.toLong())
            if (isCorrect) {
                sum+=correctResult.toLong()
            }
        }
        return sum
    }

    fun calculateExpression(left: Long, right: Long, terms: List<Long>, correctResult: Long): Boolean {
        if (terms.isEmpty()) {
            if (left + right == correctResult) {
                println("Correct result: $left + $right = $correctResult")
                return true
            }
            if (left * right == correctResult) {
                println("Correct result: $left * $right = $correctResult")
                return true
            }
            return false
        }

        val nextTerm = terms[0]

        return calculateExpression(left + right, nextTerm, terms.drop(1), correctResult) ||
        calculateExpression(left * right, nextTerm, terms.drop(1), correctResult)
    }

    fun part2(input: List<String>): Long {
        var sum = 0L
        input.forEach { line ->
            val parts = line.split(":")
            val correctResult = parts[0]
            val terms = parts[1].split(" ").drop(1).map { it.toLong() }

            val isCorrect = calculateExpressionPart2(terms[0], terms[1], terms.drop(2), correctResult.toLong())
            if (isCorrect) {
                sum+=correctResult.toLong()
            }
        }
        return sum
    }

    fun calculateExpressionPart2(left: Long, right: Long, terms: List<Long>, correctResult: Long): Boolean {
        if (terms.isEmpty()) {
            if (left + right == correctResult) {
                println("Correct result: $left + $right = $correctResult")
                return true
            }
            if (left * right == correctResult) {
                println("Correct result: $left * $right = $correctResult")
                return true
            }
            if ("$left$right" == correctResult.toString()) {
                println("Correct result: $left$right = $correctResult")
                return true
            }
            return false
        }

        val nextTerm = terms[0]
        return calculateExpressionPart2(left + right, nextTerm, terms.drop(1), correctResult) ||
                calculateExpressionPart2(left * right, nextTerm, terms.drop(1), correctResult) ||
                calculateExpressionPart2("$left$right".toLong(), nextTerm, terms.drop(1), correctResult)
    }

    @Test
    fun runDay() {
        val testInput = readInput("day07")

        println("Part 1: ${part1(testInput)}")
        println("Part 2: ${part2(testInput)}")
    }
}
package day02

import readInput
import kotlin.math.abs
import kotlin.test.Test

class Day02 {


    fun isInvalidReport(numbers: List<Int>): Boolean {
        var ascending: Boolean? = null
        var previousNumber: Int? = null

        var invalidReport = false

        numbers.forEach { number ->
            if (previousNumber != null) {
                val diff = number - previousNumber!!
                if (diff == 0 || abs(diff) > 3) {
                    invalidReport = true
                }
                if (ascending == null) {
                    if (diff > 0)
                        ascending = true
                    else ascending = false
                } else {
                    if (ascending == true && diff < 0) {
                        invalidReport = true
                    }
                    if (ascending == false && diff > 0) {
                        invalidReport = true
                    }
                }
            }
            previousNumber = number
        }
        return invalidReport
    }

    fun part1(input: List<String>): Int {
        val correctReports = input.mapNotNull { report ->
            val numbers = report.split(" ").map { it.toInt() }

            if (isInvalidReport(numbers)) {
                null
            } else {
                "Report with line $report is valid"
            }
        }

        correctReports.forEach { println(it) }
        return correctReports.size
    }

    fun part2(input: List<String>): Int {
        val correctReports = input.mapNotNull { report ->
            val numbers = report.split(" ").map { it.toInt() }
            if (isInvalidReport(numbers)) {

                var reportCouldBeFixedWithRemovingALevel = false
                for (i in numbers.indices) {
                    val reducedReport = numbers.toMutableList()
                    reducedReport.removeAt(i)

                    if (!isInvalidReport(reducedReport)) {
                        reportCouldBeFixedWithRemovingALevel = true
                    }
                }

                if (reportCouldBeFixedWithRemovingALevel) {
                    "Report with line $report could be fixed."
                } else null
            } else {
                "Report with line $report is valid"
            }
        }

        correctReports.forEach { println(it) }
        return correctReports.size
    }

    @Test
    fun runDay() {
        val testInput = readInput("day02")

        println("Part 1: ${part1(testInput)}")
        println("Part 2: ${part2(testInput)}")
    }
}
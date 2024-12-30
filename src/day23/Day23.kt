package day23

import readInput
import readTest
import kotlin.test.Test

class Day23 {

    val validTriples = mutableListOf<String>()

    fun part1(input: List<String>): Int {

        val links = mutableListOf<String>()
        input.forEach {
            links.add(it)
        }

        links.forEach { link ->
            val values = link.split("-")
            val key = values[0]
            val entry = values[1]

            val keyLinks = links.filter { it.startsWith(key) && it != link }
            keyLinks.forEach { keyLink ->
                val otherEntry = keyLink.split("-")[1]

                if (links.contains("$entry-$otherEntry") || links.contains("$otherEntry-$entry")) {
                    // valid comb: key, entry, other entry
                    val triple = "$key-$entry-$otherEntry"
                    if (!alreadyExisting(triple)) {
                        validTriples.add(triple)
                    }
                }
            }

            val entryLinks = links.filter { it.endsWith(key) && it != link }
            entryLinks.forEach { entryLink ->
                val otherEntry = entryLink.split("-")[0]
                if (links.contains("$key-$otherEntry") || links.contains("$otherEntry-$key")) {
                    // valid comb: key, entry, other entry
                    validTriples.add("$key-$entry-$otherEntry")
                }
            }
        }

        return validTriples.filter {
            it.elementAt(0).toString() == "t" ||
                    it.elementAt(3).toString() == "t" ||
                    it.elementAt(6).toString() == "t"
        }.size
    }

    private fun alreadyExisting(triple: String): Boolean {
        val values = triple.split("-")
        val one = values[0]
        val two = values[1]
        val three = values[2]

        if (validTriples.contains("$one-$two-$three")) {
            return true
        }
        if (validTriples.contains("$one-$three-$two")) {
            return true
        }
        if (validTriples.contains("$two-$one-$three")) {
            return true
        }
        if (validTriples.contains("$two-$three-$one")) {
            return true
        }
        if (validTriples.contains("$three-$two-$one")) {
            return true
        }
        if (validTriples.contains("$three-$one-$two")) {
            return true
        }
        return false
    }


    fun part2(input: List<String>): Int {
        return 0
    }

    @Test
    fun runDay() {
        val testInput = readTest("day23")

        println("Part 1: ${part1(testInput)}")
        println("Part 2: ${part2(testInput)}")
    }
}
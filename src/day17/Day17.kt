package day17

import readInput
import readTest
import kotlin.math.pow
import kotlin.test.Test

class Day17 {

    data class Computa8Bit(
        var regA: Int = 0,
        var regB: Int = 0,
        var regC: Int = 0,

        var instructionPointer:Int = 0
    ) {
        fun run(instruction: Instruction, operand: Int): String {
            when (instruction) {
                Instruction.ADV -> {
                    regA =  (regA / 2.0.pow(combo(operand).toDouble())).toInt()
                }
                Instruction.BXL -> {
                    regB = regB xor operand
                }
                Instruction.BST -> {
                    regB = combo(operand) % 8
                }
                Instruction.JNZ -> {
                    if (regA != 0) {
                        instructionPointer = operand
                    }
                }
                Instruction.BXC -> {
                    regB = regB xor regC
                }
                Instruction.OUT -> {
                    return "${combo(operand) % 8},"
                }
                Instruction.BDV -> {
                    regB =  (regA / 2.0.pow(combo(operand).toDouble())).toInt()
                }
                Instruction.CDV -> {
                    regC =  (regA / 2.0.pow(combo(operand).toDouble())).toInt()
                }
            }
            return ""
        }

        private fun combo(operand: Int): Int {
            if (operand == 0) {
                return 0
            }
            if (operand == 1) {
                return 1
            }
            if (operand == 2) {
                return 2
            }
            if (operand == 3) {
                return 3
            }
            if (operand == 4) {
                return regA
            }
            if (operand == 5) {
                return regB
            }
            if (operand == 6) {
                return regC
            }
            throw IllegalArgumentException("Invalid combo operand $operand")
        }
    }

    enum class Instruction(val code: Int) {
        ADV(0),
        BXL(1),
        BST(2),
        JNZ(3),
        BXC(4),
        OUT(5),
        BDV(6),
        CDV(7);

        companion object {
            fun of(code: Int): Day17.Instruction {
                return entries.find { it.code == code }!!
            }
        }
    }

    fun part1(input: List<String>): String {

        val computa = Computa8Bit()

        val iterator = input.iterator()

        var line = iterator.next()
        computa.regA = line.substring(line.indexOf(":")+2).toInt()
        line = iterator.next()
        computa.regB = line.substring(line.indexOf(":")+2).toInt()
        line = iterator.next()
        computa.regC = line.substring(line.indexOf(":")+2).toInt()

        //skip line break
        line = iterator.next()

        line = iterator.next()
        val code = line.substring(line.indexOf(":")+2).split(",")

        println(computa)
        println(code)

        var out = ""
        while (true) {
            if (computa.instructionPointer >= code.size) {
                break
            }

            val instruction = Instruction.of(code[computa.instructionPointer].toInt())
            val operand = code[computa.instructionPointer+1].toInt()

            val instructionPointer = computa.instructionPointer
            out += computa.run(instruction, operand)

            if (instructionPointer == computa.instructionPointer) {
                computa.instructionPointer += 2
            }
        }

        return out.dropLast(1)
    }

    fun part2(input: List<String>): Int {
        val computa = Computa8Bit()

        val iterator = input.iterator()

        var line = iterator.next()
        computa.regA = line.substring(line.indexOf(":")+2).toInt()
        line = iterator.next()
        computa.regB = line.substring(line.indexOf(":")+2).toInt()
        line = iterator.next()
        computa.regC = line.substring(line.indexOf(":")+2).toInt()

        //skip line break
        line = iterator.next()

        line = iterator.next()
        val origCode = line.substring(line.indexOf(":")+2) + ","
        val code = line.substring(line.indexOf(":")+2).split(",")

        println(computa)
        println(code)

        var out = ""
        for (i in 0..Int.MAX_VALUE) {
            computa.regA = i
            computa.regB = 0
            computa.regC = 0
            computa.instructionPointer = 0
            out = ""
            while (true) {
                if (computa.instructionPointer >= code.size) {
                    break
                }

                val instruction = Instruction.of(code[computa.instructionPointer].toInt())
                val operand = code[computa.instructionPointer+1].toInt()

                val instructionPointer = computa.instructionPointer
                out += computa.run(instruction, operand)

                if (instructionPointer == computa.instructionPointer) {
                    computa.instructionPointer += 2
                }

                if (!origCode.startsWith(out)) {
                    break
                }
            }

            if (i % 1000000 == 0) {
                println(i)
            }

            if (out == origCode) {
                return i
            }
        }
        return -1
    }

    @Test
    fun runDay() {
        val testInput = readInput("day17")

        println("Part 1: ${part1(testInput)}")
        println("Part 2: ${part2(testInput)}")
    }
}
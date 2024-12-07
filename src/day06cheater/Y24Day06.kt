package day06cheater

import println
import java.io.File
import java.io.FileNotFoundException
import java.util.*

/**
 * see: https://adventofcode.com/2024/day/6
 * https://github.com/Friends-of-AoC/Advent-of-Code-2024-exchange/tree/main/exchange/day06/feri/java
 */
object Y24Day06 {
    const val DIR_NORTH: Int = 0

    val DIR_CHARS: CharArray = charArrayOf('^', '>', 'v', '<')
    val DIR_VECTORS: Array<Pos> = arrayOf(Pos(0, -1), Pos(1, 0), Pos(0, 1), Pos(-1, 0))

    fun turnRight(dir: Int): Int {
        return (dir + 1) % 4
    }

    @Throws(FileNotFoundException::class)
    fun mainPart2(inputfile: String) {
        val lab = Lab()
        Scanner(File(inputfile)).use { scanner ->
            while (scanner.hasNext()) {
                val line = scanner.nextLine().trim { it <= ' ' }
                if (line.isBlank()) {
                    continue
                }
                lab.addRow(line)
            }
        }
        lab.findGuardPos()
        var countLoops = 0

        var loops: MutableList<Pos> = mutableListOf()
        for (y in 0..<lab.maxY) {
            for (x in 0..<lab.maxX) {
                if (lab.isFree(x, y)) {
                    val testLab = Lab(lab)
                    //					System.out.println("BEFORE "+new Pos(x,y)+": "+testLab);
                    testLab.set(x, y, '#')
                    //					System.out.println("AFTER "+new Pos(x,y)+": "+testLab);
                    while (testLab.tick()) {
                    }
                    if (testLab.isLoop) {
                        loops.add(Pos(x, y))
                        countLoops++
                    }
                }
            }
        }

        loops.sortBy { it.x }
        loops.forEach(::println)

        println("LOOPS: $countLoops")
    }


    @Throws(FileNotFoundException::class)
    @JvmStatic
    fun main(args: Array<String>) {
//		System.out.println("--- PART I  ---");
//        /**/        mainPart1("exchange/day06/feri/input-example.txt");
//		mainPart1("exchange/day06/feri/input.txt");
//		System.out.println("---------------");
        println()
        println("--- PART II ---")
        //		mainPart2("exchange/day06/feri/input-example.txt");
        mainPart2("src/day06cheater/input.txt")
        println("---------------")
    }


    @JvmRecord
    data class Pos(val x: Int, val y: Int) {
        override fun toString(): String {
            return "($x,$y)"
        }

        fun add(other: Pos): Pos {
            return add(other.x, other.y)
        }

        fun add(dx: Int, dy: Int): Pos {
            return Pos(x + dx, y + dy)
        }

        fun move(dir: Int): Pos {
            return add(DIR_VECTORS[dir])
        }
    }

    class Lab {
        private val rows: MutableList<String>
        var maxX: Int
        var maxY: Int

        var guardPos: Pos?
        var guardDir: Int

        var ticks: Int

        var visitedPositions: MutableMap<Pos?, Char>?

        constructor() {
            rows = ArrayList()
            maxX = 0
            maxY = 0
            guardPos = null
            guardDir = -1
            visitedPositions = null
            ticks = 0
        }

        constructor(other: Lab) {
            this.rows = ArrayList(other.rows)
            this.maxX = other.maxX
            this.maxY = other.maxY
            this.guardPos = other.guardPos
            this.guardDir = other.guardDir
            this.visitedPositions = HashMap(other.visitedPositions)
            this.ticks = other.ticks
        }

        fun addRow(row: String) {
            rows.add(row)
            maxX = row.length
            maxY = rows.size
        }

        fun get(pos: Pos): Char {
            return get(pos.x, pos.y)
        }

        fun get(x: Int, y: Int): Char {
            if ((x < 0) || (x >= maxX) || (y < 0) || (y >= maxY)) {
                return '+'
            }
            return rows[y][x]
        }

        fun isFree(x: Int, y: Int): Boolean {
            return get(x, y) == '.'
        }

        fun isBlocked(pos: Pos): Boolean {
            return get(pos) == '#'
        }

        fun isOutside(pos: Pos): Boolean {
            return get(pos) == '+'
        }

        val isLoop: Boolean
            get() {
                val c = visitedPositions!![guardPos]
                return (c != null) && (c == DIR_CHARS[guardDir])
            }

        fun findGuardPos() {
            for (y in 0..<maxY) {
                for (x in 0..<maxX) {
                    if (get(x, y) == '^') {
                        guardPos = Pos(x, y)
                        guardDir = DIR_NORTH
                        visitedPositions = HashMap()
                        visitedPositions!![guardPos] = '^'
                        return
                    }
                }
            }
            throw RuntimeException("start pos not found")
        }

        fun tick(): Boolean {
            ticks++
            var nextPos = guardPos!!.move(guardDir)
            while (isBlocked(nextPos)) {
                guardDir = turnRight(guardDir)
                nextPos = guardPos!!.move(guardDir)
            }
            guardPos = nextPos
            if (isLoop) {
                return false
            }
            if (isOutside(guardPos!!)) {
                return false
            }
            visitedPositions!![guardPos] = DIR_CHARS[guardDir]
            return true
        }

        override fun toString(): String {
            val result = StringBuilder()
            result.append("SHAPE (" + maxX + "," + maxY + "), TICKS " + ticks + ", POS " + guardPos + ", DIR " + DIR_CHARS[guardDir] + "\n")
            for (y in 0..<maxY) {
                for (x in 0..<maxX) {
                    val p = Pos(x, y)
                    var c = get(p)
                    if (visitedPositions!!.containsKey(p)) {
                        c = visitedPositions!![p]!!
                    }
                    result.append(c)
                }
                result.append('\n')
            }
            return result.toString()
        }

        fun set(x: Int, y: Int, c: Char) {
            val row = rows[y]
            val newRow = row.substring(0, x) + c + row.substring(x + 1)
            rows[y] = newRow
        }
    }
}
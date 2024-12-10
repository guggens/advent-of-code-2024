package day09

import readInput
import readTest
import java.io.File
import java.io.FileNotFoundException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.test.Test


class Day09 {

    fun part1(input: List<String>): Long {
        val line = input.first()

        val transformedLine = transformLine(line).toCharArray()
        for (n in 0 until transformedLine.size) {
            print(transformedLine[n].toString())
            if (n > 200) break
        }
        println()
        for (n in transformedLine.size-1 downTo  0) {
            print(transformedLine[n].toString())
            if (n < transformedLine.size-200) break
        }
        println()

        println("SIZE: ${transformedLine.size}")

        var endCounter = transformedLine.size - 1
        var startCounter = 0

        while (startCounter < endCounter) {
            val char = transformedLine[startCounter].toString()
            if (char != ".") {
                startCounter++
                continue
            } else {

                var endChar = transformedLine[endCounter].toString()
                while (endChar == ".") {
                    endCounter--
                    endChar = transformedLine[endCounter].toString()
                }
                transformedLine[startCounter] = transformedLine[endCounter]
                transformedLine[endCounter] = '.'

                startCounter++
                endCounter--

                //println(transformedLine)
            }
        }

        for (n in 0 until transformedLine.size) {
            print(transformedLine[n].toString())
            if (n > 200) break
        }
        println()
        for (n in transformedLine.size-1 downTo  0) {
            print(transformedLine[n].toString())
            if (n < transformedLine.size-200) break
        }
        println()
        println("SIZE: ${transformedLine.size}")

        var sum = 0L
        for (i in 0..transformedLine.size) {
            val char = transformedLine[i].toString()
            if (char == ".") {
                break
            } else {
                sum += (char.toLong() * i)
            }
        }

        return sum
    }


    private fun transformLine(line: String): String {
        var id = 0
        var index = 0
        val result = StringBuilder()

        var useBlock = true
        while (index < line.length) {
            val char = line[index++].toString().toInt()
            if (useBlock) {
                for (i in 0 until char) {
                    result.append(id)
                }
                id++
            } else {
                for (i in 0 until char) {
                    result.append('.')
                }
            }
            useBlock = !useBlock
        }
        return result.toString()
    }


    fun part2(input: List<String>): Int {
        return 0
    }

    @Test
    fun runDay() {
        val testInput = readInput("day09")

        println("Part 1: ${part1(testInput)}")
        println("Part 1: ${mainPart1(testInput.first())}")
        //println("Part 2: ${part2(testInput)}")
    }


    fun mainPart1(inputfile: String) : Long {
        val chars = inputfile.toCharArray()

        var solution: Long = 0

        Scanner(File("./src/day09/input.txt")).use { scanner ->
            while (scanner.hasNext()) {
//                var line: String = scanner.nextLine().trim()
//                if (line.isBlank()) {
//                    continue
//                }

//        for (i in chars.indices) {
                // var line: String = chars[i].toString()
                var line: String = scanner.nextLine().trim()
                if (line.isBlank()) {
                    continue
                }
                val disk = ArrayList<Int>()
                line = line + "0"
                for (i in 0 until line.length / 2) {
                    val nFile = line[2 * i].code - '0'.code
                    val nSpace = line[2 * i + 1].code - '0'.code
                    for (n in 0 until nFile) {
                        disk.add(i)
                    }
                    for (n in 0 until nSpace) {
                        disk.add(-1)
                    }
                }

                for (n in 0 until disk.size) {
                    print(disk[n].toString())
                    if (n > 200) break
                }
                println()


                //println(disk)
                for (i in 0..disk.size) {
                    try {
                        while (disk[i] == -1) {
                            val v = disk.removeAt(disk.size - 1)
                            disk[i] = v
                            //						System.out.println(disk);
                        }
                    } catch (e: IndexOutOfBoundsException) {}

                }
                //println(disk)

                for (n in 0 until disk.size) {
                    print(disk[n].toString())
                    if (n > 200) break
                }
                println()
                for (i in disk.indices) {
                    while (disk[i] == -1) {
                        val v = disk.removeAt(disk.size - 1)
                        disk[i] = v
                        //						System.out.println(disk);
                    }
                }

                for (i in disk.indices) {
                    solution += (disk[i] * i).toLong()
                }

            }
        }
//
//        Scanner(File(inputfile)).use { scanner ->
//            while (scanner.hasNext()) {
//                var line: String = scanner.nextLine().trim()
//                if (line.isBlank()) {
//                    continue
//                }
//                val disk = ArrayList<Int>()
//                line = line + "0"
//                for (i in 0 until line.length / 2) {
//                    val nFile = line[2 * i].code - '0'.code
//                    val nSpace = line[2 * i + 1].code - '0'.code
//                    for (n in 0 until nFile) {
//                        disk.add(i)
//                    }
//                    for (n in 0 until nSpace) {
//                        disk.add(-1)
//                    }
//                }
//                println(disk)
//                for (i in disk.indices) {
//                    while (disk[i] == -1) {
//                        val v = disk.removeAt(disk.size - 1)
//                        disk[i] = v
//                        //						System.out.println(disk);
//                    }
//                }
//                var solution: Long = 0
//                for (i in disk.indices) {
//                    solution += (disk[i] * i).toLong()
//                }
//                println(solution)
//            }
//        }

//        C:\Users\RayJa\.jdks\corretto-21.0.5\bin\java.exe -ea -Didea.test.cyclic.buffer.size=1048576 "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA 2024.2.4\lib\idea_rt.jar=56390:C:\Program Files\JetBrains\IntelliJ IDEA 2024.2.4\bin" -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath "C:\Program Files\JetBrains\IntelliJ IDEA 2024.2.4\lib\idea_rt.jar;C:\Program Files\JetBrains\IntelliJ IDEA 2024.2.4\plugins\junit\lib\junit5-rt.jar;C:\Program Files\JetBrains\IntelliJ IDEA 2024.2.4\plugins\junit\lib\junit-rt.jar;C:\Users\RayJa\advent-of-code-kotlin-template\build\classes\kotlin\main;C:\Users\RayJa\.gradle\caches\modules-2\files-2.1\org.jetbrains.kotlin\kotlin-test-junit5\2.1.0\3d386bb97b74923e498d46193f7a380de10734a4\kotlin-test-junit5-2.1.0.jar;C:\Users\RayJa\.gradle\caches\modules-2\files-2.1\org.jetbrains.kotlin\kotlin-test\2.1.0\bab38f61fdb1c90a68fce9666b3f9fe419aeef72\kotlin-test-2.1.0.jar;C:\Users\RayJa\.gradle\caches\modules-2\files-2.1\org.jetbrains.kotlin\kotlin-stdlib\2.1.0\85f8b81009cda5890e54ba67d64b5e599c645020\kotlin-stdlib-2.1.0.jar;C:\Users\RayJa\.gradle\caches\modules-2\files-2.1\org.junit.jupiter\junit-jupiter-api\5.10.1\eb90c7d8bfaae8fdc97b225733fcb595ddd72843\junit-jupiter-api-5.10.1.jar;C:\Users\RayJa\.gradle\caches\modules-2\files-2.1\org.jetbrains\annotations\13.0\919f0dfe192fb4e063e7dacadee7f8bb9a2672a9\annotations-13.0.jar;C:\Users\RayJa\.gradle\caches\modules-2\files-2.1\org.junit.platform\junit-platform-commons\1.10.1\2bfcd4a4e38b10c671b6916d7e543c20afe25579\junit-platform-commons-1.10.1.jar;C:\Users\RayJa\.gradle\caches\modules-2\files-2.1\org.opentest4j\opentest4j\1.3.0\152ea56b3a72f655d4fd677fc0ef2596c3dd5e6e\opentest4j-1.3.0.jar;C:\Users\RayJa\.gradle\caches\modules-2\files-2.1\org.apiguardian\apiguardian-api\1.1.2\a231e0d844d2721b0fa1b238006d15c6ded6842a\apiguardian-api-1.1.2.jar;C:\Users\RayJa\.gradle\caches\modules-2\files-2.1\org.junit.platform\junit-platform-launcher\1.10.1\ce85bf5e38bee0989ded9bd468dd4ff78dc0cfa8\junit-platform-launcher-1.10.1.jar;C:\Users\RayJa\.gradle\caches\modules-2\files-2.1\org.junit.jupiter\junit-jupiter-engine\5.10.1\6c9ff773f9aa842b91d1f2fe4658973252ce2428\junit-jupiter-engine-5.10.1.jar;C:\Users\RayJa\.gradle\caches\modules-2\files-2.1\org.junit.platform\junit-platform-engine\1.10.1\f32ae4af74fde68414b8a3d2b7cf1fb43824a83a\junit-platform-engine-1.10.1.jar" com.intellij.rt.junit.JUnitStarter -ideVersion5 -junit5 day09.Day09,runDay
//        000000-1-1-1111-1-1-1-1-1-1-1222222-1333-1-1-1-1-1-14444444-1-1-1-15555-1-1-1666666-1-1-1-1-1-1-1-1777-1-1-1888888-1-1-1-1-1-1-1-1-199999-1-1-1-1-11010101010101010-1-1111111-1-1-1-1-1-1-1-11212-1-1-1-1-113-1-1-1-1-1-1-1-11414-1-1-1-1-1-1-1-1-115151515-1-1-1-1-116161616161616-1-1-1-1171717171717171717-1-1-118181818-1-1-1-1-1-1-11919-12020202020202020-1-1
//        000000999999999999111999899989997999799979997999722222299973339996999699969996999699964444444999599959995999555559995999499946666669993999299929992999299929992999277799919991999188888899919991999199909990999099899989998999999998899889988998899881010101010101010998899881111119988998799879987998799879987998612129986998699869986998613998699869986998599859985998599851414998599859985998499849984998499839983151515159983998399839983998316161616161616998399839982998217171717171717171799829981998118181818998199809980998099809980998019199980202020202020202099799979
//        Part 1: 6288599492129


        return solution
    }
}
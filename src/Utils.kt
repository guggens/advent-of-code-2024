import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readTest(name: String) = Path("src/$name/test.txt").readText().trim().lines()

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name/input.txt").readText().trim().lines()

fun transposeMatrix(matrix: Array<Array<Char>>): Array<Array<Char>> {
    val transposed = Array(matrix[0].size) { Array(matrix.size) { ' ' } }
    for (i in matrix.indices) {
        for (j in matrix[i].indices) {
            transposed[j][i] = matrix[i][j]
        }
    }
    return transposed
}

enum class Direction {
    UP, DOWN, LEFT, RIGHT;

    fun turn90Right(): Direction {
        if (this == UP) return RIGHT
        if (this == RIGHT) return DOWN
        if (this == DOWN) return LEFT
        return UP
    }

    fun invert(): Direction {
        if (this == UP) return DOWN
        if (this == DOWN) return UP
        if (this == LEFT) return RIGHT
        return LEFT
    }
}

data class Position(val x: Int, val y: Int) {

    fun moveInDirection(direction: Direction): Position {
        when (direction) {
            Direction.UP -> return Position(x, y - 1)
            Direction.DOWN -> return Position(x, y + 1)
            Direction.LEFT -> return Position(x - 1, y)
            Direction.RIGHT -> return Position(x + 1, y)
        }

    }

    fun isInBounds(max: Int): Boolean {
        return x >= 0 && y >= 0 && x < max && y < max
    }
}

class Matrix(input: List<String>) {

    var matrix: Array<Array<Char>> = transposeMatrix(input.map { it.toCharArray().toTypedArray() }.toTypedArray())

    fun transpose() {
        matrix = transposeMatrix(matrix)
    }

    fun findChar(char: Char): Position {
        for (i in matrix.indices) {
            for (j in matrix[0].indices) {
                if (matrix[i][j] == char) {
                    return Position(i, j)
                }
            }
        }
        return Position(-1, -1)
    }

    fun getChar(pos: Position): Char {
        return matrix[pos.x][pos.y]
    }

    fun setChar(c: Char, pos: Position) {
        matrix[pos.x][pos.y] = c
    }

    override fun toString(): String {
        val builder = StringBuilder()

        for (x in 0..<matrix.size) {
            for (y in 0..<matrix[0].size) {
                builder.append(matrix[x][y])
            }
            builder.append("\n")
        }

        return builder.toString()
    }


}

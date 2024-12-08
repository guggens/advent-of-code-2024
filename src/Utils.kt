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

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun transposeMatrix(matrix: Array<Array<Char>>): Array<Array<Char>> {
    val transposed = Array(matrix[0].size) { Array(matrix.size) { ' ' } }
    for (i in matrix.indices) {
        for (j in matrix[i].indices) {
            transposed[j][i] = matrix[i][j]
        }
    }
    return transposed
}
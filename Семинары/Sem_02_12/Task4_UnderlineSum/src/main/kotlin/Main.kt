import kotlin.random.Random

fun main(args: Array<String>) {
    val n: Int = readln().toInt()
    val matrix: Array<Array<Int>> = Array(n) { _ -> Array(n) { _ -> Random.nextInt(11)}}
    for (row in matrix) {
        for (element in row) {
            print("$element  ")
        }
        println()
    }

    var sum = 0
    for (i in 0 until n) {
        for (j in 0 until n) {
            if (i + j > n - 1) {
                sum += matrix[i][j]
            }
        }
    }
    println("Сумма: $sum")
}
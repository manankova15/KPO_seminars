private fun readlnStrings() = readln().split(' ')
private fun readIntArray() = readlnStrings().run { IntArray(size) { get(it).toInt() } }

fun main(args: Array<String>) {
    var arr:IntArray = readIntArray()
    var count = 1
    var previous = arr[0]

    for (i in 1 until arr.size) {
        if (arr[i] != previous) {
            ++count
            previous = arr[i]
        }
    }
    println("Количество уникальных значени $count")
}
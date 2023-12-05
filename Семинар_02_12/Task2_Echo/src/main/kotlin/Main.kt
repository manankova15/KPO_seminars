fun main(args: Array<String>) {
    var line = readln()
    println(line)
    val split = line.split(" ")
    for (i in 1..3)
        println(split[split.size - 1])
}
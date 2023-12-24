fun blueText(text: String):String {
    return "\u001B[34m$text\u001B[0m"
}
fun errorText(text: String): String {
    return "\u001B[31mError: $text\u001B[0m"
}

fun customFormat(text: String): String {
    return "-------$text-------"
}

package System

import java.io.File
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class DataRepository<T: Identifiable>(private val clazz: Class<T>, private val filePath: String) {
    private var data = mutableListOf<T>()

    init {
        loadData()
    }

    fun saveData() {
        try {
            when (clazz.simpleName) {
                "Film" -> {
                    "films.json".also { File("$filePath/$it").writeText(Json.encodeToString(data as MutableList<Film>)) }
                }
                "Session" -> {
                    "sessions.json".also { File("$filePath/$it").writeText(Json.encodeToString(data as MutableList<Session>)) }
                }
                "Seat" -> {
                    "seats.json".also { File("$filePath/$it").writeText(Json.encodeToString(data as MutableList<Seat>)) }
                }
                "Ticket" -> {
                    "tickets.json".also { File("$filePath/$it").writeText(Json.encodeToString(data as MutableList<Ticket>)) }
                }
                "User" -> {
                    "users.json".also { File("$filePath/$it").writeText(Json.encodeToString(data as MutableList<User>)) }
                }
                else -> throw IllegalArgumentException("Unsupported class: ${clazz.simpleName}")
            }
        } catch (e: Exception) {
            handleException(e, "Warnings while saving ${clazz.simpleName}")
        }
    }

    private fun loadData() {
        try {
            val file = File("$filePath/${clazz.simpleName.lowercase()}s.json")

            if (file.exists()) {
                val read = file.readText(Charsets.UTF_8)
                if (read.isNotBlank()) {
                    @Suppress("UNCHECKED_CAST")
                    data = when (clazz.simpleName) {
                        "Film" -> Json.decodeFromString<MutableList<Film>>(read) as MutableList<T>
                        "Session" -> Json.decodeFromString<MutableList<Session>>(read) as MutableList<T>
                        "Seat" -> Json.decodeFromString<MutableList<Seat>>(read) as MutableList<T>
                        "Ticket" -> Json.decodeFromString<MutableList<Ticket>>(read) as MutableList<T>
                        "User" -> Json.decodeFromString<MutableList<User>>(read) as MutableList<T>
                        else -> throw IllegalArgumentException("Unsupported class: ${clazz.simpleName}")
                    }
                }
            }
        } catch (e: Exception) {
            handleException(e, "Warnings while reading ${clazz.simpleName}")
        }
    }

    private fun handleException(e: Exception, message: String) {
        println(e.printStackTrace())
        println("\u001B[31m$message\u001B[0m")
    }

    fun add(item: T) {
        data.add(item)
        saveData()
    }

    fun getAll(): List<T> = data

    fun remove(id: Int) {
        val itemToRemove = data.find { it.id == id }
        if (itemToRemove != null) {
            data.remove(itemToRemove)
            saveData()
        }
    }
}

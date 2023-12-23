package System

import kotlinx.serialization.KSerializer
import java.io.File
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class DataRepository<T>(private val clazz: Class<T>, private val filePath: String) {
    private var data = mutableListOf<T>()

    init {
        loadData()
    }

    /*private fun saveData(serializer: KSerializer<T>)
        {try {
            val fileName = "$filePath/${clazz.simpleName.lowercase()}s.json"

            val json = Json { prettyPrint = true }

            val jsonData = json.encodeToString(serializer, data)

            File(fileName).writeText(jsonData)
        } catch (e: Exception) {
            handleException(e, "Warnings while saving ${clazz.simpleName}")
        }
    }*/

    private fun saveData() {
        try {
            when (clazz.simpleName) {
                "Film" -> {
                    val dataList = data as MutableList<Film>
                    "films.json".also { File("$filePath/$it").writeText(Json.encodeToString(dataList)) }
                }
                "Session" -> {
                    val dataList = data as MutableList<Session>
                    "sessions.json".also { File("$filePath/$it").writeText(Json.encodeToString(dataList)) }
                }
                "Seat" -> {
                    val dataList = data as MutableList<Seat>
                    "seats.json".also { File("$filePath/$it").writeText(Json.encodeToString(dataList)) }
                }
                "Ticket" -> {
                    val dataList = data as MutableList<Ticket>
                    "tickets.json".also { File("$filePath/$it").writeText(Json.encodeToString(dataList)) }
                }
                "User" -> {
                    val dataList = data as MutableList<User>
                    "users.json".also { File("$filePath/$it").writeText(Json.encodeToString(dataList)) }
                }
                else -> throw IllegalArgumentException("Unsupported class: ${clazz.simpleName}")
            }
        } catch (e: Exception) {
            handleException(e, "Warnings while saving ${clazz.simpleName}")
        }
    }

    /*private fun saveData() {
        try {
            File("$filePath/${clazz.simpleName.lowercase()}s.json").writeText(Json.encodeToString(data as MutableList<T>))
        } catch (e: Exception) {
            handleException(e, "Warnings while saving ${clazz.simpleName}")
        }
    }*/
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




    /*private fun loadData() {
        try {
            val file = File("$filePath/${clazz.simpleName.lowercase()}s.json")

            if (file.exists()) {
                val read = file.readText(Charsets.UTF_8)
                if (read.isNotBlank()) {
                    data = Json.decodeFromString<MutableList<T>>(read)
                }
            }
        } catch (e: Exception) {
            handleException(e, "Warnings while reading ${clazz.simpleName}")
        }
    }*/


    /*private fun loadData() {
        try {
            val read = File("$filePath/${clazz.simpleName.lowercase()}s.json").readText(Charsets.UTF_8)
            data = Json.decodeFromString<MutableList<T>>(read)
        } catch (e: Exception) {
            handleException(e, "Warnings while reading ${clazz.simpleName}")
        }
    }*/

    private fun handleException(e: Exception, message: String) {
        println(e.printStackTrace())
        println("\u001B[31m$message\u001B[0m")
    }

    fun add(item: T) {
        data.add(item)
        saveData()
    }

    fun getAll(): List<T> = data
}

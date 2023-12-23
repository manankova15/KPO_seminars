package System

import java.io.File
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException

class DataRepository(private val filePath: String) {
    private var films = mutableListOf<Film>()
    private var sessions = mutableListOf<Session>()
    private var tickets = mutableListOf<Ticket>()
    private var users = mutableListOf<User>()

    init {
        // Регистрация модуля JavaTimeModule для поддержки java.time.* типов
        // ObjectMapper().registerModule(JavaTimeModule())
        loadData()
    }

    private fun saveData() {
        try {
            File("$filePath/films.json").writeText(Json.encodeToString<MutableList<Film>>(films))
        } catch (e : Exception) {
            println(e.printStackTrace())
            println("\u001B[31mWarnings while reading films\u001B[0m")
        }
        try {
            File("$filePath/sessions.json").writeText(Json.encodeToString<MutableList<Session>>(sessions))
        } catch (e : Exception) {
            println(e.printStackTrace())
            println("\u001B[31mWarnings while reading sessions\u001B[0m")
        }
        try {
            File("$filePath/tickets.json").writeText(Json.encodeToString<MutableList<Ticket>>(tickets))
        } catch (e : Exception) {
            println(e.printStackTrace())
            println("\u001B[31mWarnings while reading tickets\u001B[0m")
        }
        try {
            File("$filePath/users.json").writeText(Json.encodeToString<MutableList<User>>(users))
        } catch (e : Exception) {
            println(e.printStackTrace())
            println("\u001B[31mWarnings while reading users\u001B[0m")
        }
    }

    //private val json = Json { ignoreUnknownKeys = true }
    private fun loadData() {
        try {
            var read1 = File("$filePath/films.json").readText(Charsets.UTF_8)
            films = Json.decodeFromString<MutableList<Film>>(read1)
        } catch (e : Exception) {
            println(e.printStackTrace())
            println("\u001B[31mWarnings while reading films\u001B[0m")
        }
        try {
            var read2 = File("$filePath/sessions.json").readText(Charsets.UTF_8)
            sessions = Json.decodeFromString<MutableList<Session>>(read2)
        } catch (e : Exception) {
            println(e.printStackTrace())
            println("\u001B[31mWarnings while reading sessions\u001B[0m")
        }
        try {
            var read3 = File("$filePath/tickets.json").readText(Charsets.UTF_8)
            tickets = Json.decodeFromString<MutableList<Ticket>>(read3)
        } catch (e : Exception) {
            println(e.printStackTrace())
            println("\u001B[31mWarnings while reading tickets\u001B[0m")
        }
        try {
            var read4 = File("$filePath/users.json").readText(Charsets.UTF_8)
            users = Json.decodeFromString<MutableList<User>>(read4)

        } catch (e : Exception) {
            println(e.printStackTrace())
            println("\u001B[31mWarnings while reading users\u001B[0m")
        }
        /*loadDataFromFile<List<Film>>("$filePath/films.json")?.let { films.addAll(it) }
        loadDataFromFile<List<Session>>("$filePath/sessions.json")?.let { sessions.addAll(it) }
        loadDataFromFile<List<Ticket>>("$filePath/tickets.json")?.let { tickets.addAll(it) }
        loadDataFromFile<List<User>>("$filePath/users.json")?.let { users.addAll(it) }*/
    }

    /*private inline fun <reified T> loadDataFromFile(filePath: String): T? {
        try {
            val file = File(filePath)
            if (file.exists() && file.isFile() && file.length() > 0) {
                val jsonString = file.readText()
                if (jsonString.isNotBlank()) {
                    return Json.decodeFromString(jsonString)
                }
            }
        } catch (e: Exception) {
            // Обработка ошибок при чтении файла или десериализации данных
            // e.printStackTrace()
        }
        return null
    }*/

    fun addFilm(film: Film) {
        films.add(film)
        saveData()
    }

    fun getFilms(): List<Film> = films

    fun addSession(session: Session) {
        sessions.add(session)
        saveData()
    }

    fun getSessions(): List<Session> = sessions

    fun sellTicket(ticket: Ticket) {
        tickets.add(ticket)
        saveData()
    }

    fun getTickets(): List<Ticket> = tickets

    fun addUser(user: User) {
        users.add(user)
        saveData()
    }

    fun getUsers(): List<User> = users
}
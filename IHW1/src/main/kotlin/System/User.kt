package System

import kotlinx.serialization.Serializable
var UserId = (DataRepository(User::class.java, "data").getAll().maxByOrNull { it.id }?.id ?: 0).toInt()

@Serializable
data class User(
    val login: String,
    val hashedPassword: String
): Identifiable {
    override var id : Int = 0
    init {
        id = ++UserId
    }
    override fun toString(): String {
        return "User: id=$id, login=$login "
    }
}
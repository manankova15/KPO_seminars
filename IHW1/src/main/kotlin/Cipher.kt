import org.mindrot.jbcrypt.BCrypt

// Пример шифрования пароля
fun hashPassword(password: String): String {
    return BCrypt.hashpw(password, BCrypt.gensalt())
}

// Пример проверки пароля
fun checkPassword(inputPassword: String, hashedPassword: String): Boolean {
    return BCrypt.checkpw(inputPassword, hashedPassword)
}
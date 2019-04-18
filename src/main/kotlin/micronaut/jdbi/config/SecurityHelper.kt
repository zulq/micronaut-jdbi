package micronaut.jdbi.config

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.inject.Singleton

@Singleton
class SecurityHelper {
    private val encoder = BCryptPasswordEncoder()

    fun encodePwd(input: String): String {
        return encoder.encode(input)
    }

    fun passwordValid(input: String, hashPwd: String): Boolean {
        return encoder.matches(input, hashPwd)
    }
}
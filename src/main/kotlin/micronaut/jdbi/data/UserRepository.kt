package micronaut.jdbi.data

import io.micronaut.security.authentication.UserDetails
import micronaut.jdbi.config.DbConfig
import micronaut.jdbi.config.SecurityHelper
import micronaut.jdbi.models.Authorities
import micronaut.jdbi.models.User
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.kotlin.onDemand
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import javax.inject.Singleton

interface UserRepository {
    @SqlUpdate("insert into user (username, password) values(:username, :password)")
    @GetGeneratedKeys
    fun save(@BindBean user: User): Long?

    @SqlQuery("select * from user where username=:username")
    fun getUserByUsername(@Bind("username") username: String): User?

    @SqlQuery("select * from authorities where username=:username")
    fun getRolesByUsername(@Bind("username") username: String): List<Authorities>
}

@Singleton
class UsersService(private val securityHelper: SecurityHelper) {
    private val db = DbConfig.getInstance()

    fun getUserByUsername(username: String): User? {
        return try {
            db.onDemand<UserRepository>().getUserByUsername(username)
        } catch (x: Exception) {
            null
        }
    }

    fun save(user: User): Long? {
        return try {
            user.password = securityHelper.encodePwd(user.password)
            db.onDemand<UserRepository>().save(user)
        } catch (x: Exception) {
            println(x.message)
            null
        }
    }

    private fun isUserValid(username: String, password: String): Boolean {
        val user = getUserByUsername(username)
        return (user != null && securityHelper.passwordValid(password, user.password))
    }

    fun authenticate(username: String, password: String): UserDetails? {
        try {
            val user = getUserByUsername(username)
            if (user != null && isUserValid(username, password)) {
                val roles = getUserRoles(username).map { it.authority }
                return UserDetails(user.username, java.util.ArrayList<String>(roles))
            }
        } catch (x: Exception) {
            println(x.message)
        }
        return null
    }

    fun isUsernameTaken(username: String): Boolean {
        return getUserByUsername(username) != null
    }

    private fun getUserRoles(username: String): List<Authorities> {
        return db.onDemand<UserRepository>().getRolesByUsername(username)
    }
}
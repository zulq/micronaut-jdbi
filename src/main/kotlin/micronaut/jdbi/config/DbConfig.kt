package micronaut.jdbi.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import org.jdbi.v3.core.Jdbi
import javax.inject.Singleton
import javax.sql.DataSource

@Singleton
class DbConfig {
    companion object {
        fun getInstance(): Jdbi {
            val jdbi = Jdbi.create(DbConfig().dataSource())
            jdbi.installPlugins()
            return jdbi
        }
    }

    @Factory
    @Replaces(DataSource::class)
    fun dataSource(): DataSource {
        var config = HikariConfig()
        config.jdbcUrl = "jdbc:mysql://localhost:your_port/students?serverTimezone=UTC"
        config.username = "your_username"
        config.password = "your_pwd"
        return HikariDataSource(config)
    }
}
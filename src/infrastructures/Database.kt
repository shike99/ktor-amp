package ktor.amp.infrastructures

import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.config.HoconApplicationConfig
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

object Users: IntIdTable() {
    val name = varchar("name", 50).uniqueIndex()
    val password = varchar("password", 100)
}

object Articles: IntIdTable() {
    val title = varchar("title", 100)
    val description = varchar("description", 1000)
    val userId = reference("user_id", Users).index()
}

object Items: IntIdTable() {
    val content = varchar("content", 1000)
    val type = varchar("type", 50)
    val sectionId = integer("section_id")
    val articleId = reference("article_id", Articles).index()
}

@KtorExperimentalAPI
object Database {
    private val appConfig = HoconApplicationConfig(ConfigFactory.load())
    private val driver = appConfig.property("db.driver").getString()
    private val dbUrl = appConfig.property("db.url").getString()
    private val dbUser = appConfig.property("db.user").getString()
    private val dbPassword = appConfig.property("db.password").getString()

    fun bootstrap() {
        Database.connect(generateDataSource())
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(Articles, Users, Items)
        }
    }

    private fun generateDataSource(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = driver
        config.jdbcUrl = dbUrl
        config.username = dbUser
        config.password = dbPassword
        config.maximumPoolSize = 5
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }
}
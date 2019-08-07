package ktor.amp.useCases.article

import ktor.amp.respositories.ArticleRepository
import ktor.amp.respositories.UserRepository
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

class ArticleUseCaseImpl: ArticleUseCase {
    override fun list(): Iterable<ArticleRepository> = ArticleRepository.all()

    override fun findById(id: EntityID<Int>): ArticleRepository = ArticleRepository(id)

    override fun create(): ArticleRepository? {
        var article: ArticleRepository? = null
        transaction {
            addLogger(StdOutSqlLogger)
            article = ArticleRepository.new {
                title = "title"
                description = "description"
                user = UserRepository.findById(1)
            }
        }
        return article
    }
}
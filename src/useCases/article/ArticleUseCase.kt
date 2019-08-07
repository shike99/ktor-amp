package ktor.amp.useCases.article

import ktor.amp.respositories.ArticleRepository
import org.jetbrains.exposed.dao.EntityID

interface ArticleUseCase {
    fun list(): Iterable<ArticleRepository>
    fun findById(id: EntityID<Int>): ArticleRepository
    fun create(): ArticleRepository?
}
package ktor.amp.respositories

import ktor.amp.infrastructures.Articles
import ktor.amp.infrastructures.Items
import ktor.amp.infrastructures.Users
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class ArticleRepository(id: EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<ArticleRepository>(Articles)

    var title by Articles.title
    var description by Articles.description
    var user by UserRepository referencedOn Articles.userId
    val itemList by ItemRepository referrersOn Items.articleId
}
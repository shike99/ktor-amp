package ktor.amp.respositories

import ktor.amp.infrastructures.Articles
import ktor.amp.infrastructures.Users
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class UserRepository(id: EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<UserRepository>(Users)

    var name by Users.name
    var password by Users.password
    val articleList by ArticleRepository referrersOn Articles.userId
}

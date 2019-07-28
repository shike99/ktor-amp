package ktor.amp.respositories

import ktor.amp.infrastructures.Items
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class ItemRepository(id: EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<ItemRepository>(Items)

    var type by Items.type
    var content by Items.content
    var sectionId by Items.sectionId
    var article by ArticleRepository referencedOn Items.articleId
}
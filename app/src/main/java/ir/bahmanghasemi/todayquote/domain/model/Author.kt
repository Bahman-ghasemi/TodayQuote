package ir.bahmanghasemi.todayquote.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SerialName("author")
@Serializable
data class Author(
    val id: String,
    val bio: String,
    val shape: Int = 0,
    val description: String,
    val link: String,
    val name: String,
    val quoteCount: Int,
    val slug: String,
    val dateAdded: String,
    val dateModified: String
)
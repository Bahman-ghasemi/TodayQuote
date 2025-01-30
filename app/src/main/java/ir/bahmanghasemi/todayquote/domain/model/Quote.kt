package ir.bahmanghasemi.todayquote.domain.model

data class Quote(
    val id: String,
    val author: String,
    val authorSlug: String,
    val content: String,
    val isFavorite: Boolean = false,
    val dateAdded: String,
    val dateModified: String,
    val length: Int,
    val tags: List<String>
)
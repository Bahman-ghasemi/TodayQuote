package ir.bahmanghasemi.todayquote.data.data_source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Quote")
data class QuoteEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val author: String,
    val authorSlug: String,
    val content: String,
    val dateAdded: String,
    val dateModified: String,
    val length: Int,
    val tags: List<String>
)
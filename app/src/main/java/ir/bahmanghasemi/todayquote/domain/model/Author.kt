package ir.bahmanghasemi.todayquote.domain.model

import com.google.gson.annotations.SerializedName
import ir.bahmanghasemi.todayquote.common.data.util.Shape
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SerialName("author")
@Serializable
data class Author(
    @SerializedName("_id")
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
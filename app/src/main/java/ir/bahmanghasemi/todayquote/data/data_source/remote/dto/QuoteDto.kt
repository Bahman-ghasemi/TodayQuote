package ir.bahmanghasemi.todayquote.data.data_source.remote.dto

import com.google.gson.annotations.SerializedName

data class QuoteDto(
    @SerializedName("_id")
    val id: String,
    val author: String,
    val authorSlug: String,
    val content: String,
    val dateAdded: String,
    val dateModified: String,
    val length: Int,
    val tags: List<String>
)
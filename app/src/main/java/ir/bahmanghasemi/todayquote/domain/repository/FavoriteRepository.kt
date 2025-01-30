package ir.bahmanghasemi.todayquote.domain.repository

import ir.bahmanghasemi.todayquote.data.data_source.local.entity.QuoteEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun makeFavorite(quote: QuoteEntity): Long
    suspend fun unFavorite(quote: QuoteEntity): Int
    suspend fun isFavorite(uid: String): Boolean
    fun favoriteQuotes(): Flow<List<QuoteEntity>>
}
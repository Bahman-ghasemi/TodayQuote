package ir.bahmanghasemi.todayquote.data.repository

import ir.bahmanghasemi.todayquote.data.data_source.local.dao.QuoteDao
import ir.bahmanghasemi.todayquote.data.data_source.local.entity.QuoteEntity
import ir.bahmanghasemi.todayquote.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val dao: QuoteDao
) : FavoriteRepository {

    override suspend fun makeFavorite(quote: QuoteEntity): Long {
        return dao.insert(quote)
    }

    override suspend fun unFavorite(quote: QuoteEntity): Int {
        return dao.delete(quote)
    }

    override suspend fun isFavorite(uid: String): Boolean {
        return dao.isFavorite(uid)
    }

    override fun favoriteQuotes(): Flow<List<QuoteEntity>> {
        return dao.favoriteQuotes()
    }

}
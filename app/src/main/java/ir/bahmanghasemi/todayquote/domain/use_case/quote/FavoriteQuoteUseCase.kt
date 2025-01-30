package ir.bahmanghasemi.todayquote.domain.use_case.quote

import ir.bahmanghasemi.todayquote.data.data_source.local.entity.QuoteEntity
import ir.bahmanghasemi.todayquote.data.data_source.mapper.QuoteEntityMapper
import ir.bahmanghasemi.todayquote.domain.model.Quote
import ir.bahmanghasemi.todayquote.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteQuoteUseCase @Inject constructor(
    private val repository: FavoriteRepository,
    private val mapper: QuoteEntityMapper
) {

    suspend fun makeFavorite(quote: Quote): Long {
        return repository.makeFavorite(mapper.toData(quote))
    }

    suspend fun unFavorite(quote: QuoteEntity): Int {
        return repository.unFavorite(quote)
    }

    suspend fun isFavorite(uid: String): Boolean {
        return repository.isFavorite(uid)
    }

    fun favoriteQuotes(): Flow<List<Quote>> {
        return repository.favoriteQuotes().map { favorites ->
            favorites.map {
                mapper.toDomain(it)
            }
        }
    }
}
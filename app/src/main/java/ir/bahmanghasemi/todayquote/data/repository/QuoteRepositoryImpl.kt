package ir.bahmanghasemi.todayquote.data.repository

import ir.bahmanghasemi.todayquote.data.data_source.remote.QuoteApi
import ir.bahmanghasemi.todayquote.domain.model.Quote
import ir.bahmanghasemi.todayquote.domain.repository.QuoteRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class QuoteRepositoryImpl @Inject constructor(
    private val api: QuoteApi
) : QuoteRepository {

    override suspend fun getRandomQuote(): Response<Quote> {
        val filter = mutableMapOf<String, Int>()
        filter["minLength"] = 100
        filter["maxLength"] = 150
        return api.getRandomQuote(filter)
    }

    override suspend fun getQuotes(filter: Map<String, Any>): Response<List<Quote>> {
        return api.getQuotes(filter)
    }

}
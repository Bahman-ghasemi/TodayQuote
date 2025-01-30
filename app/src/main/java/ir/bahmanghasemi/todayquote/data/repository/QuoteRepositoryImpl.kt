package ir.bahmanghasemi.todayquote.data.repository

import ir.bahmanghasemi.todayquote.data.data_source.remote.QuoteApi
import ir.bahmanghasemi.todayquote.data.data_source.remote.dto.QuoteDto
import ir.bahmanghasemi.todayquote.domain.model.ResponseHandler
import ir.bahmanghasemi.todayquote.domain.repository.QuoteRepository
import retrofit2.Response
import javax.inject.Inject

class QuoteRepositoryImpl @Inject constructor(
    private val api: QuoteApi
) : QuoteRepository {

    override suspend fun fetchRandomQuote(): Response<QuoteDto> {
        val filter = mutableMapOf<String, Int>()
        filter["minLength"] = 100
        filter["maxLength"] = 150
        return api.fetchRandomQuote(filter)
    }

    override suspend fun fetchQuotes(filter: Map<String, String>): Response<ResponseHandler<QuoteDto>> {
        return api.fetchQuotes(filter)
    }

}
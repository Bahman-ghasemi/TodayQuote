package ir.bahmanghasemi.todayquote.domain.repository

import ir.bahmanghasemi.todayquote.data.data_source.remote.dto.QuoteDto
import ir.bahmanghasemi.todayquote.domain.model.ResponseHandler
import retrofit2.Response

interface QuoteRepository {
    suspend fun fetchRandomQuote(): Response<QuoteDto>
    suspend fun fetchQuotes(filter: Map<String, String>): Response<ResponseHandler<QuoteDto>>
}
package ir.bahmanghasemi.todayquote.domain.repository

import ir.bahmanghasemi.todayquote.domain.model.Quote
import ir.bahmanghasemi.todayquote.domain.model.ResponseHandler
import retrofit2.Response

interface QuoteRepository {
    suspend fun getRandomQuote(): Response<Quote>
    suspend fun getQuotes(filter: Map<String, String>): Response<ResponseHandler<Quote>>
}
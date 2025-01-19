package ir.bahmanghasemi.todayquote.domain.repository

import ir.bahmanghasemi.todayquote.domain.model.Quote
import retrofit2.Response

interface QuoteRepository {
    suspend fun getRandomQuote(): Response<Quote>
    suspend fun getQuotes(filter: Map<String, Any>): Response<List<Quote>>
}
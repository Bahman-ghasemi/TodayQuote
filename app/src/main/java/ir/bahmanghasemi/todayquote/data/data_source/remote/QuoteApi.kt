package ir.bahmanghasemi.todayquote.data.data_source.remote

import ir.bahmanghasemi.todayquote.domain.model.Quote
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface QuoteApi {

    @GET("random")
    suspend fun getRandomQuote(@QueryMap filter: Map<String, Any>?): Response<Quote>

    @GET("quotes/random")
    suspend fun getQuotes(@QueryMap filter: Map<String, Any>?): Response<List<Quote>>

}
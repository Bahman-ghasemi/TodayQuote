package ir.bahmanghasemi.todayquote.data.data_source.remote

import ir.bahmanghasemi.todayquote.domain.model.Quote
import ir.bahmanghasemi.todayquote.domain.model.ResponseHandler
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface QuoteApi {

    @GET("random")
    suspend fun getRandomQuote(@QueryMap filter: Map<String, Int> = emptyMap()): Response<Quote>

    @GET("quotes")
    suspend fun getQuotes(@QueryMap filter: Map<String, String> = emptyMap()): Response<ResponseHandler<Quote>>

}
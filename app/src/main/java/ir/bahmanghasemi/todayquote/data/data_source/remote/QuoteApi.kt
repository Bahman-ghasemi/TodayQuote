package ir.bahmanghasemi.todayquote.data.data_source.remote

import ir.bahmanghasemi.todayquote.data.data_source.remote.dto.QuoteDto
import ir.bahmanghasemi.todayquote.domain.model.ResponseHandler
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface QuoteApi {

    @GET("random")
    suspend fun fetchRandomQuote(@QueryMap filter: Map<String, Int> = emptyMap()): Response<QuoteDto>

    @GET("quotes")
    suspend fun fetchQuotes(@QueryMap filter: Map<String, String> = emptyMap()): Response<ResponseHandler<QuoteDto>>

}
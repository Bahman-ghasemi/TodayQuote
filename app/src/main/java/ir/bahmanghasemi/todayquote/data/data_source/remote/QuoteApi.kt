package ir.bahmanghasemi.todayquote.data.data_source.remote

import ir.bahmanghasemi.todayquote.data.data_source.remote.dto.QuoteDto
import ir.bahmanghasemi.todayquote.domain.model.Quote
import ir.bahmanghasemi.todayquote.domain.model.ResponseHandler
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface QuoteApi {

    @GET("random")
    suspend fun getRandomQuote(@QueryMap filter: Map<String, Int> = emptyMap()): Response<QuoteDto>

    @GET("quotes")
    suspend fun getQuotes(@QueryMap filter: Map<String, String> = emptyMap()): Response<ResponseHandler<QuoteDto>>

}
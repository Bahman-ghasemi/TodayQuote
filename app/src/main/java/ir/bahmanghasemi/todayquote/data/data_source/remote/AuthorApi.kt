package ir.bahmanghasemi.todayquote.data.data_source.remote

import ir.bahmanghasemi.todayquote.domain.model.Author
import retrofit2.Response
import retrofit2.http.GET

interface AuthorApi {

    @GET("authors")
    suspend fun getAuthors(filter: Map<String, String> = emptyMap()): Response<List<Author>>
}
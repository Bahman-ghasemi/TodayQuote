package ir.bahmanghasemi.todayquote.data.data_source.remote

import ir.bahmanghasemi.todayquote.data.data_source.remote.dto.AuthorDto
import ir.bahmanghasemi.todayquote.domain.model.Author
import ir.bahmanghasemi.todayquote.domain.model.ResponseHandler
import retrofit2.Response
import retrofit2.http.GET

interface AuthorApi {

    @GET("authors")
    suspend fun getAuthors(): Response<ResponseHandler<AuthorDto>>
}
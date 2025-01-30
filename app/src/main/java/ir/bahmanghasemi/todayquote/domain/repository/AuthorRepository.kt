package ir.bahmanghasemi.todayquote.domain.repository

import ir.bahmanghasemi.todayquote.data.data_source.remote.dto.AuthorDto
import ir.bahmanghasemi.todayquote.domain.model.Author
import ir.bahmanghasemi.todayquote.domain.model.ResponseHandler
import retrofit2.Response

interface AuthorRepository {
    suspend fun getAuthors(filter: Map<String, String>): Response<ResponseHandler<AuthorDto>>
}
package ir.bahmanghasemi.todayquote.domain.repository

import ir.bahmanghasemi.todayquote.domain.model.Author
import retrofit2.Response

interface AuthorRepository {
    suspend fun getAuthors(filter: Map<String, String>): Response<List<Author>>
}
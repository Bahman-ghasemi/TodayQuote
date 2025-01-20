package ir.bahmanghasemi.todayquote.data.repository

import ir.bahmanghasemi.todayquote.data.data_source.remote.AuthorApi
import ir.bahmanghasemi.todayquote.domain.model.Author
import ir.bahmanghasemi.todayquote.domain.repository.AuthorRepository
import retrofit2.Response
import javax.inject.Inject

class AuthorRepositoryImpl @Inject constructor(
    private val api: AuthorApi
): AuthorRepository {

    override suspend fun getAuthors(filter: Map<String, String>): Response<List<Author>> {
        return api.getAuthors()
    }
}
package ir.bahmanghasemi.todayquote.domain.use_case.author

import ir.bahmanghasemi.todayquote.domain.model.Author
import ir.bahmanghasemi.todayquote.domain.model.ResponseHandler
import ir.bahmanghasemi.todayquote.domain.repository.AuthorRepository
import retrofit2.Response
import javax.inject.Inject

class AuthorUseCase @Inject constructor(
    private val repository: AuthorRepository
) {
    suspend operator fun invoke(query: String): Response<ResponseHandler<Author>> {
        val map = if (query.isNotEmpty()) mapOf("slug" to query) else emptyMap()
        return repository.getAuthors(map)
    }
}
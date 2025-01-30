package ir.bahmanghasemi.todayquote.domain.use_case.quote

import ir.bahmanghasemi.todayquote.data.data_source.remote.dto.QuoteDto
import ir.bahmanghasemi.todayquote.domain.model.ResponseHandler
import ir.bahmanghasemi.todayquote.domain.repository.QuoteRepository
import retrofit2.Response
import javax.inject.Inject

class AuthorQuotesUseCase @Inject constructor(
    private val repository: QuoteRepository
) {
    suspend operator fun invoke(filter: Map<String, String>) : Response<ResponseHandler<QuoteDto>> {
        return repository.fetchQuotes(filter)
    }
}
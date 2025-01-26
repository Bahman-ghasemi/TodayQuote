package ir.bahmanghasemi.todayquote.domain.use_case.quote

import ir.bahmanghasemi.todayquote.domain.model.Quote
import ir.bahmanghasemi.todayquote.domain.model.ResponseHandler
import ir.bahmanghasemi.todayquote.domain.repository.QuoteRepository
import retrofit2.Response
import javax.inject.Inject

class QuotesUseCase @Inject constructor(
    private val repository: QuoteRepository
) {
    suspend operator fun invoke(filter: Map<String, String>) : Response<ResponseHandler<Quote>> {
        return repository.getQuotes(filter)
    }
}
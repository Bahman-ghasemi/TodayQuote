package ir.bahmanghasemi.todayquote.domain.use_case.quote

import ir.bahmanghasemi.todayquote.domain.model.Quote
import ir.bahmanghasemi.todayquote.domain.repository.QuoteRepository
import retrofit2.Response
import javax.inject.Inject

class DailyQuoteUseCase @Inject constructor(
    private val repository: QuoteRepository
) {
    suspend operator fun invoke() : Response<Quote> {
        return repository.getRandomQuote()
    }
}
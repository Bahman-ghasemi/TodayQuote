package ir.bahmanghasemi.todayquote.domain.use_case

data class QuoteUseCase(
    val dailyUseCase: DailyQuoteUseCase,
    val quotesUseCase: QuotesUseCase
)

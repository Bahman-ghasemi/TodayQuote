package ir.bahmanghasemi.todayquote.domain.use_case.quote

data class QuoteUseCase(
    val dailyUseCase: DailyQuoteUseCase,
    val authorQuotesUseCase: AuthorQuotesUseCase,
    val favoriteUseCase: FavoriteQuoteUseCase
)

package ir.bahmanghasemi.todayquote.presentation.daily_quote

import ir.bahmanghasemi.todayquote.domain.model.Quote

data class QuotesUiState(
    val quotes: List<Quote>? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)
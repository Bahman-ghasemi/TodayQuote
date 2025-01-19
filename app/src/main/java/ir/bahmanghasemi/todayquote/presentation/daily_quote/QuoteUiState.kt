package ir.bahmanghasemi.todayquote.presentation.daily_quote

import ir.bahmanghasemi.todayquote.domain.model.Quote

data class QuoteUiState(
    val quote: Quote? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)
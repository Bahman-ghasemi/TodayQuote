package ir.bahmanghasemi.todayquote.presentation.favorite

import ir.bahmanghasemi.todayquote.domain.model.Quote

data class FavoriteUiState(
    val favorites: List<Quote>? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)

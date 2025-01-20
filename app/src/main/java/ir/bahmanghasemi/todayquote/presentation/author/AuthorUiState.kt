package ir.bahmanghasemi.todayquote.presentation.author

import ir.bahmanghasemi.todayquote.domain.model.Author

data class AuthorUiState(
    val authors: List<Author>? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)

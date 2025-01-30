package ir.bahmanghasemi.todayquote.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.bahmanghasemi.todayquote.domain.use_case.quote.FavoriteQuoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val useCase: FavoriteQuoteUseCase
) : ViewModel() {

    private val _favoriteUiState = MutableStateFlow(FavoriteUiState())
    val favoriteUiState = _favoriteUiState.asStateFlow()

    fun getFavorites() {
        viewModelScope.launch {
            _favoriteUiState.update { FavoriteUiState(isLoading = true) }
            try {
                useCase.favoriteQuotes().filterNotNull().collectLatest { favorites ->
                    _favoriteUiState.update { FavoriteUiState(favorites = favorites) }
                }
                _favoriteUiState.update { FavoriteUiState(isLoading = false) }
            } catch (ex: Exception) {
                _favoriteUiState.update { FavoriteUiState(errorMessage = ex.localizedMessage) }
            }
        }
    }

}
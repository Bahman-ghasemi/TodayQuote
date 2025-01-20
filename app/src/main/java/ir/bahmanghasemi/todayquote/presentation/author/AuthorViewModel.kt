package ir.bahmanghasemi.todayquote.presentation.author

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.bahmanghasemi.todayquote.common.data.util.Connectivity
import ir.bahmanghasemi.todayquote.common.presentation.util.Extension
import ir.bahmanghasemi.todayquote.domain.use_case.author.AuthorUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorViewModel @Inject constructor(
    private val useCase: AuthorUseCase,
    private val application: Application
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(AuthorUiState())
    val uiState = _uiState.asStateFlow()

    fun getAuthors(query: String) {
        _uiState.update { AuthorUiState(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            if (Connectivity.isNetworkAvailable(application.applicationContext)) {
                try {
                    val response = useCase.invoke(query)
                    if (response.isSuccessful) {
                        response.body()?.let { authors ->
                            _uiState.update { AuthorUiState(authors = authors) }
                        }
                    } else {
                        _uiState.update { AuthorUiState(errorMessage = response.message()) }
                    }

                } catch (ex: Exception) {
                    _uiState.update { AuthorUiState(errorMessage = ex.localizedMessage) }
                }
            } else {
                _uiState.update { AuthorUiState(errorMessage = "not Internet Connection Available!") }
            }
        }
    }
}
package ir.bahmanghasemi.todayquote.presentation.daily_quote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.bahmanghasemi.todayquote.R
import ir.bahmanghasemi.todayquote.common.data.util.Connectivity
import ir.bahmanghasemi.todayquote.domain.use_case.DailyQuoteUseCase
import ir.bahmanghasemi.todayquote.domain.use_case.QuoteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val quoteUseCase: QuoteUseCase,
    private val application: Application
) : AndroidViewModel(application) {

    private val _quoteUiState = MutableStateFlow(QuoteUiState())
    val quoteUiState = _quoteUiState.asStateFlow()

    fun getRandomQuote() {
        viewModelScope.launch(Dispatchers.IO) {
            if (Connectivity.isNetworkAvailable(application.applicationContext)) {
                try {
                    val response = quoteUseCase.dailyUseCase.invoke()
                    if (response.isSuccessful) {
                        response.body()?.let { quote ->
                            _quoteUiState.update { QuoteUiState(quote = quote) }
                        }
                    } else {
                        _quoteUiState.update { QuoteUiState(errorMessage = response.message()) }
                    }
                } catch (ex: Exception) {
                    _quoteUiState.update { QuoteUiState(errorMessage = ex.localizedMessage) }
                }

            } else {
                _quoteUiState.update { QuoteUiState(errorMessage = application.applicationContext.getString(R.string.networkNotAvailable)) }
            }
        }
    }

}
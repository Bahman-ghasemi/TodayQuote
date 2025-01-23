package ir.bahmanghasemi.todayquote.presentation.daily_quote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.bahmanghasemi.todayquote.R
import ir.bahmanghasemi.todayquote.common.data.util.Connectivity
import ir.bahmanghasemi.todayquote.domain.use_case.quote.QuoteUseCase
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

    private val _quotesUiState = MutableStateFlow(QuotesUiState())
    val quotesUiState = _quotesUiState.asStateFlow()

    fun getRandomQuote() {
        _quoteUiState.update { QuoteUiState(isLoading = true) }
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
                        print("error-log-> ${response.message()}")
                    }
                } catch (ex: Exception) {
                    _quoteUiState.update { QuoteUiState(errorMessage = ex.localizedMessage) }
                    print("error-log-> ${ex.localizedMessage}")
                }

            } else {
                _quoteUiState.update { QuoteUiState(errorMessage = application.applicationContext.getString(R.string.networkNotAvailable)) }
                print("error-log-> no internet connection!")
            }
        }
    }

    fun getAuthorQuotes(authorSlug: String) {
        _quotesUiState.update { QuotesUiState(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            if (Connectivity.isNetworkAvailable(application.applicationContext)) {
                try {
                    val response = quoteUseCase.quotesUseCase.invoke(mapOf("author" to authorSlug))
                    if (response.isSuccessful) {
                        response.body()?.let { quotesResponse ->
                            _quotesUiState.update { QuotesUiState(quotes = quotesResponse.results) }
                        }
                    } else {
                        _quotesUiState.update { QuotesUiState(errorMessage = response.message()) }
                        print("error-log-> ${response.message()}")
                    }
                } catch (ex: Exception) {
                    _quotesUiState.update { QuotesUiState(errorMessage = ex.localizedMessage) }
                    print("error-log-> ${ex.localizedMessage}")
                }

            } else {
                _quotesUiState.update { QuotesUiState(errorMessage = application.applicationContext.getString(R.string.networkNotAvailable)) }
                print("error-log-> no internet connection!")
            }
        }
    }

}
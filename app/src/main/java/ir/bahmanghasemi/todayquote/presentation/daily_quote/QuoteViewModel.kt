package ir.bahmanghasemi.todayquote.presentation.daily_quote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.bahmanghasemi.todayquote.R
import ir.bahmanghasemi.todayquote.common.data.util.Connectivity
import ir.bahmanghasemi.todayquote.data.data_source.mapper.QuoteDtoMapper
import ir.bahmanghasemi.todayquote.data.data_source.mapper.QuoteEntityMapper
import ir.bahmanghasemi.todayquote.domain.model.Quote
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
    private val entityMapper: QuoteEntityMapper,
    private val application: Application
) : AndroidViewModel(application) {

    private val _quoteUiState = MutableStateFlow(QuoteUiState())
    val quoteUiState = _quoteUiState.asStateFlow()

    private val _quotesUiState = MutableStateFlow(QuotesUiState())
    val quotesUiState = _quotesUiState.asStateFlow()

    fun fetchRandomQuote() {
        _quoteUiState.update { QuoteUiState(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            if (Connectivity.isNetworkAvailable(application.applicationContext)) {
                try {
                    val response = quoteUseCase.dailyUseCase.invoke()
                    if (response.isSuccessful) {
                        response.body()?.let {
                            val quote = QuoteDtoMapper().toDomain(it)
                            val isFavorite = quoteUseCase.favoriteUseCase.isFavorite(quote.id)

                            _quoteUiState.update {
                                QuoteUiState(
                                    quote = quote.copy(isFavorite = isFavorite)
                                )
                            }
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

    fun fetchAuthorQuotes(authorSlug: String) {
        _quotesUiState.update { QuotesUiState(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            if (Connectivity.isNetworkAvailable(application.applicationContext)) {
                try {
                    val response = quoteUseCase.authorQuotesUseCase.invoke(mapOf("author" to authorSlug))
                    if (response.isSuccessful) {
                        response.body()?.let { quotesResponse ->
                            _quotesUiState.update { QuotesUiState(quotes = quotesResponse.results.map { QuoteDtoMapper().toDomain(it) }) }
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

    fun makeFavorite(quote: Quote) {
        viewModelScope.launch {
            quoteUseCase.favoriteUseCase.makeFavorite(quote)
            _quoteUiState.update {
                it.copy(quote = quote.copy(isFavorite = true))
            }
        }
    }

    fun makeUnFavorite(quote: Quote) {
        viewModelScope.launch {
            quoteUseCase.favoriteUseCase.unFavorite(entityMapper.toData(quote))
            _quoteUiState.update {
                it.copy(quote = quote.copy(isFavorite = false))
            }
        }
    }

}
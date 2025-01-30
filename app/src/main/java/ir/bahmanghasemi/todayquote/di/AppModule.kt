package ir.bahmanghasemi.todayquote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.bahmanghasemi.todayquote.data.data_source.local.dao.QuoteDao
import ir.bahmanghasemi.todayquote.data.data_source.mapper.AuthorDtoMapper
import ir.bahmanghasemi.todayquote.data.data_source.mapper.QuoteDtoMapper
import ir.bahmanghasemi.todayquote.data.data_source.mapper.QuoteEntityMapper
import ir.bahmanghasemi.todayquote.data.data_source.remote.AuthorApi
import ir.bahmanghasemi.todayquote.data.data_source.remote.QuoteApi
import ir.bahmanghasemi.todayquote.data.repository.AuthorRepositoryImpl
import ir.bahmanghasemi.todayquote.data.repository.FavoriteRepositoryImpl
import ir.bahmanghasemi.todayquote.data.repository.QuoteRepositoryImpl
import ir.bahmanghasemi.todayquote.domain.repository.AuthorRepository
import ir.bahmanghasemi.todayquote.domain.repository.FavoriteRepository
import ir.bahmanghasemi.todayquote.domain.repository.QuoteRepository
import ir.bahmanghasemi.todayquote.domain.use_case.author.AuthorUseCase
import ir.bahmanghasemi.todayquote.domain.use_case.quote.AuthorQuotesUseCase
import ir.bahmanghasemi.todayquote.domain.use_case.quote.DailyQuoteUseCase
import ir.bahmanghasemi.todayquote.domain.use_case.quote.FavoriteQuoteUseCase
import ir.bahmanghasemi.todayquote.domain.use_case.quote.QuoteUseCase

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideQuoteRepository(api: QuoteApi): QuoteRepository {
        return QuoteRepositoryImpl(api)
    }

    @Provides
    fun provideFavoriteRepository(dao: QuoteDao): FavoriteRepository {
        return FavoriteRepositoryImpl(dao)
    }

    @Provides
    fun providesQuoteUseCase(quoteRepo: QuoteRepository, favRepo: FavoriteRepository, quoteEntityMapper: QuoteEntityMapper): QuoteUseCase {
        return QuoteUseCase(
            dailyUseCase = DailyQuoteUseCase(quoteRepo),
            authorQuotesUseCase = AuthorQuotesUseCase(quoteRepo),
            favoriteUseCase = FavoriteQuoteUseCase(favRepo, quoteEntityMapper)
        )
    }

    @Provides
    fun provideAuthorRepository(api: AuthorApi): AuthorRepository {
        return AuthorRepositoryImpl(api)
    }

    @Provides
    fun providesAuthorUseCase(repo: AuthorRepository): AuthorUseCase {
        return AuthorUseCase(repo)
    }

    @Provides
    fun provideQuoteEntityMapper(): QuoteEntityMapper {
        return QuoteEntityMapper()
    }

    @Provides
    fun provideQuoteDtoMapper(): QuoteDtoMapper {
        return QuoteDtoMapper()
    }

    @Provides
    fun provideAuthorDtoMapper(): AuthorDtoMapper {
        return AuthorDtoMapper()
    }
}
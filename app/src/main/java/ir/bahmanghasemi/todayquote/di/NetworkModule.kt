package ir.bahmanghasemi.todayquote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.bahmanghasemi.todayquote.common.data.data_source.remote.ssl_unsafe.BypassSsl
import ir.bahmanghasemi.todayquote.common.data.util.Const
import ir.bahmanghasemi.todayquote.data.data_source.remote.AuthorApi
import ir.bahmanghasemi.todayquote.data.data_source.remote.QuoteApi
import ir.bahmanghasemi.todayquote.data.repository.AuthorRepositoryImpl
import ir.bahmanghasemi.todayquote.data.repository.QuoteRepositoryImpl
import ir.bahmanghasemi.todayquote.domain.repository.AuthorRepository
import ir.bahmanghasemi.todayquote.domain.repository.QuoteRepository
import ir.bahmanghasemi.todayquote.domain.use_case.author.AuthorUseCase
import ir.bahmanghasemi.todayquote.domain.use_case.quote.DailyQuoteUseCase
import ir.bahmanghasemi.todayquote.domain.use_case.quote.QuoteUseCase
import ir.bahmanghasemi.todayquote.domain.use_case.quote.QuotesUseCase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideOkHttpClient(bypassSsl: BypassSsl): OkHttpClient {
        val client = bypassSsl.getUnsafeOkHttpClient().newBuilder()
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        client.addInterceptor(logging)
        return client.build()
    }

    @Provides
    fun provideRetrofit(
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    fun provideQuoteApi(retrofit: Retrofit): QuoteApi {
        return retrofit.create(QuoteApi::class.java)
    }

    @Provides
    fun provideQuoteRepository(api: QuoteApi): QuoteRepository {
        return QuoteRepositoryImpl(api)
    }

    @Provides
    fun providesQuoteUseCase(repo: QuoteRepository): QuoteUseCase {
        return QuoteUseCase(
            dailyUseCase = DailyQuoteUseCase(repo),
            quotesUseCase = QuotesUseCase(repo)
        )
    }

    @Provides
    fun provideAuthorApi(retrofit: Retrofit): AuthorApi {
        return retrofit.create(AuthorApi::class.java)
    }

    @Provides
    fun provideAuthorRepository(api: AuthorApi): AuthorRepository {
        return AuthorRepositoryImpl(api)
    }

    @Provides
    fun providesAuthorUseCase(repo: AuthorRepository): AuthorUseCase {
        return AuthorUseCase(repo)
    }


}
package ir.bahmanghasemi.todayquote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.bahmanghasemi.todayquote.common.data.util.Const
import ir.bahmanghasemi.todayquote.data.data_source.remote.QuoteApi
import ir.bahmanghasemi.todayquote.data.repository.QuoteRepositoryImpl
import ir.bahmanghasemi.todayquote.domain.repository.QuoteRepository
import ir.bahmanghasemi.todayquote.domain.use_case.DailyQuoteUseCase
import ir.bahmanghasemi.todayquote.domain.use_case.QuoteUseCase
import ir.bahmanghasemi.todayquote.domain.use_case.QuotesUseCase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        client.callTimeout(20, TimeUnit.SECONDS)
        client.connectTimeout(20, TimeUnit.SECONDS)
        client.readTimeout(20, TimeUnit.SECONDS)
        client.writeTimeout(20, TimeUnit.SECONDS)
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


}
package ir.bahmanghasemi.todayquote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.bahmanghasemi.todayquote.data.data_source.remote.ssl_unsafe.BypassSsl
import ir.bahmanghasemi.todayquote.common.data.util.Const
import ir.bahmanghasemi.todayquote.data.data_source.remote.AuthorApi
import ir.bahmanghasemi.todayquote.data.data_source.remote.QuoteApi
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
    fun provideAuthorApi(retrofit: Retrofit): AuthorApi {
        return retrofit.create(AuthorApi::class.java)
    }

}
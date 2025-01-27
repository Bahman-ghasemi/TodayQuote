package ir.bahmanghasemi.todayquote.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.bahmanghasemi.todayquote.common.data.util.Const
import ir.bahmanghasemi.todayquote.data.data_source.local.Database
import ir.bahmanghasemi.todayquote.data.data_source.local.dao.QuoteDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): Database {
        return Room.databaseBuilder(
            context,
            Database::class.java,
            Const.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideQuoteDao(database: Database): QuoteDao {
        return database.dao
    }
}
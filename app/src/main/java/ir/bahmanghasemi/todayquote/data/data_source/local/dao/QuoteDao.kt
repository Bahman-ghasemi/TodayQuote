package ir.bahmanghasemi.todayquote.data.data_source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.bahmanghasemi.todayquote.data.data_source.local.entity.QuoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(quote: QuoteEntity): Long

    @Delete
    suspend fun delete(quote: QuoteEntity): Int

    @Query("SELECT * FROM Quote")
    fun favoriteQuotes(): Flow<List<QuoteEntity>>

    @Query("SELECT EXISTS(SELECT * FROM Quote WHERE id = :uid)")
    suspend fun isFavorite(uid: String): Boolean

}
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
    suspend fun insert(quote: QuoteEntity)

    @Delete
    suspend fun delete(quote: QuoteEntity)

    @Query("SELECT * FROM Quote")
    fun getFavorites(): Flow<List<QuoteEntity>>

    @Query("SELECT * FROM Quote WHERE id = :id")
    fun getFavorite(id: Int)

}
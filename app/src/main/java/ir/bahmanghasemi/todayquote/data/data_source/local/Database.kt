package ir.bahmanghasemi.todayquote.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.bahmanghasemi.todayquote.data.data_source.local.dao.QuoteDao
import ir.bahmanghasemi.todayquote.data.data_source.local.entity.QuoteEntity

@Database(entities = [QuoteEntity::class], version = 1)
abstract class Database: RoomDatabase() {
    abstract val dao:QuoteDao
}
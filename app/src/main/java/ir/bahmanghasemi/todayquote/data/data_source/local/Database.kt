package ir.bahmanghasemi.todayquote.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ir.bahmanghasemi.todayquote.data.data_source.local.dao.QuoteDao
import ir.bahmanghasemi.todayquote.data.data_source.local.entity.QuoteEntity
import ir.bahmanghasemi.todayquote.data.data_source.local.type_converter.QuoteTypeConverter

@Database(entities = [QuoteEntity::class], version = 1)
@TypeConverters(QuoteTypeConverter::class)
abstract class Database : RoomDatabase() {
    abstract val dao: QuoteDao
}
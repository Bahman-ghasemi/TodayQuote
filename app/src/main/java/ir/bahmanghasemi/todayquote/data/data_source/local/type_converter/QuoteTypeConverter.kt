package ir.bahmanghasemi.todayquote.data.data_source.local.type_converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ir.bahmanghasemi.todayquote.data.data_source.local.entity.QuoteEntity

class QuoteTypeConverter {

    @TypeConverter
    fun fromQuoteEntity(value: List<String>): String = Gson().toJson(value)

    @TypeConverter
    fun toQuoteEntity(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }
}
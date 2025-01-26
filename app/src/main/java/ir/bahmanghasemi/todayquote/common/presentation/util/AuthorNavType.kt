package ir.bahmanghasemi.todayquote.common.presentation.util

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import ir.bahmanghasemi.todayquote.domain.model.Author
import kotlinx.serialization.json.Json

object AuthorNavType {

    val AuthorType = object : NavType<Author>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): Author? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): Author {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: Author): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: Author) {
            return bundle.putString(key, Json.encodeToString(value))
        }
    }
}
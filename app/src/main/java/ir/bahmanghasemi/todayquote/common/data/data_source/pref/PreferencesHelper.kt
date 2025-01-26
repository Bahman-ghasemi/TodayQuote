package ir.bahmanghasemi.todayquote.common.data.data_source.pref

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesHelper @Inject constructor(
    private val context: Context
) {

    private val sharedPreferences by lazy {
        val masterKey = MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
        EncryptedSharedPreferences.create(
            context,
            "secure_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun <T> put(key: String, value: T) {
        with(sharedPreferences.edit()) {
            when (value) {
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Boolean -> putBoolean(key, value)
                is Float -> putFloat(key, value)
                is Long -> putLong(key, value)
                else -> throw IllegalArgumentException("Unsupported data type")
            }
            apply()
        }
    }

    fun <T> get(key: String, defaultValue: T): T {
        return with(sharedPreferences) {
            when (defaultValue) {
                is String -> getString(key, defaultValue) as T
                is Int -> getInt(key, defaultValue) as T
                is Boolean -> getBoolean(key, defaultValue) as T
                is Float -> getFloat(key, defaultValue) as T
                is Long -> getLong(key, defaultValue) as T
                else -> throw IllegalArgumentException("Unsupported data type")
            }
        }
    }

    fun remove(key: String) {
        with(sharedPreferences.edit()) {
            remove(key)
            apply()
        }
    }

    fun clear() {
        with(sharedPreferences.edit()) {
            clear()
            apply()
        }
    }
}

fun Context.encryptedPreferences(): PreferencesHelper {
    return PreferencesHelper(this)
}
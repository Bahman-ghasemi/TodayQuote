package ir.bahmanghasemi.todayquote.common.presentation.util.navigation

import ir.bahmanghasemi.todayquote.domain.model.Author
import kotlinx.serialization.Serializable

@Serializable
object DailyRoute

@Serializable
object AuthorRoute

@Serializable
data class AuthorDetailRoute(val author: Author)

@Serializable
object FavoriteRoute

@Serializable
object NotificationRoute
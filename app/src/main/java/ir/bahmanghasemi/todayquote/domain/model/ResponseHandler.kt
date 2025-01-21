package ir.bahmanghasemi.todayquote.domain.model

data class ResponseHandler<T>(
    val count: Int,
    val totalCount: Int,
    val page: Int,
    val totalPages: Int,
    val lastItemIndex: Int,
    val results: List<T>
)

package ir.bahmanghasemi.todayquote.data.data_source.mapper

import ir.bahmanghasemi.todayquote.data.data_source.local.entity.QuoteEntity
import ir.bahmanghasemi.todayquote.domain.model.Quote

class QuoteEntityMapper : Mapper<QuoteEntity, Quote> {
    override fun toDomain(data: QuoteEntity): Quote {
        return Quote(
            id = data.id,
            author = data.author,
            authorSlug = data.authorSlug,
            content = data.content,
            dateAdded = data.dateAdded,
            dateModified = data.dateModified,
            length = data.length,
            tags = data.tags
        )
    }

    override fun toData(domain: Quote): QuoteEntity {
        return QuoteEntity(
            id = domain.id,
            author = domain.author,
            authorSlug = domain.authorSlug,
            content = domain.content,
            dateAdded = domain.dateAdded,
            dateModified = domain.dateModified,
            length = domain.length,
            tags = domain.tags
        )
    }
}
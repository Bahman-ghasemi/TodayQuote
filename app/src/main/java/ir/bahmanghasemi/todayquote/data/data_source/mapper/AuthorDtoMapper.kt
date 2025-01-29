package ir.bahmanghasemi.todayquote.data.data_source.mapper

import ir.bahmanghasemi.todayquote.data.data_source.remote.dto.AuthorDto
import ir.bahmanghasemi.todayquote.domain.model.Author

class AuthorDtoMapper : Mapper<AuthorDto, Author> {
    override fun toDomain(data: AuthorDto): Author {
        return Author(
            id = data.id,
            bio = data.bio,
            shape = data.shape,
            description = data.description,
            link = data.link,
            name = data.name,
            quoteCount = data.quoteCount,
            slug = data.slug,
            dateAdded = data.dateAdded,
            dateModified = data.dateModified
        )
    }

    override fun toData(domain: Author): AuthorDto {
        return AuthorDto(
            id = domain.id,
            bio = domain.bio,
            shape = domain.shape,
            description = domain.description,
            link = domain.link,
            name = domain.name,
            quoteCount = domain.quoteCount,
            slug = domain.slug,
            dateAdded = domain.dateAdded,
            dateModified = domain.dateModified
        )
    }
}
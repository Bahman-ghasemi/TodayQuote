package ir.bahmanghasemi.todayquote.data.data_source.mapper

interface Mapper<DATA, DOMAIN> {
    fun toDomain(data: DATA): DOMAIN
    fun toData(domain: DOMAIN): DATA
}
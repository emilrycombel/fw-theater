package pl.er.code.fwtheater.domain.model

data class Page<D>(
    val content: List<D>,
    val totalElements: Long,
    val totalPages: Int,
    val number: Int,
    val size: Int,
    val numberOfElements: Int
) {
    fun hasContent(): Boolean = content.isNotEmpty()
    fun <U> map(converter: (D) -> U): Page<U> {
        return Page(
            content = content.map(converter),
            totalElements = totalElements,
            totalPages = totalPages,
            number = number,
            size = size,
            numberOfElements = numberOfElements
        )
    }

    companion object {
        fun <D> empty(): Page<D> {
            return Page(
                content = emptyList(),
                totalElements = 0,
                totalPages = 0,
                number = 0,
                size = 0,
                numberOfElements = 0
            )
        }

        fun <D> singleItem(item: D): Page<D> {
            return Page(
                content = listOf(item),
                totalElements = 1,
                totalPages = 1,
                number = 0,
                size = 1,
                numberOfElements = 1
            )
        }

        fun <D> singlePageFromList(items: List<D>): Page<D> {
            return Page(
                content = items,
                totalElements = items.size.toLong(),
                totalPages = 1,
                number = 0,
                size = items.size,
                numberOfElements = items.size
            )
        }

        fun <D> fromList(items: List<D>, pageNumber: Int, pageSize: Int, totalElements: Long): Page<D> {
            return Page(
                content = items,
                totalElements = totalElements,
                totalPages = totalElements.toInt() / pageSize,
                number = pageNumber,
                size = pageSize,
                numberOfElements = items.size
            )
        }
    }
}
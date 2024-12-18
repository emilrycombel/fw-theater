package pl.er.code.fwtheater.domain.model.search

data class MovieRatingSearchCriteria(
    override val page: Int = 0,
    override val size: Int = 50,
    override val sortBy: String?,
    override val direction: String?,
    override val id: String?,
    val movieId: String?
) : PageSearchCriteria

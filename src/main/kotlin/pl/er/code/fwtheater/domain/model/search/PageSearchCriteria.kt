package pl.er.code.fwtheater.domain.model.search

interface PageSearchCriteria {
    val page: Int
    val size: Int
    val sortBy: String?
    val direction: String?
    val id: String?
}
package pl.er.code.fwtheater.domain.model


interface MovieRating : DomainInstance<String> {
    var rating: Short?
    var comment: String?
    var ratingUser: String?
    var movie: Movie?
}
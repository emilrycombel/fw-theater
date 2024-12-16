package pl.er.code.fwtheater.domain.model


interface FilmBaseMovie : DomainInstance<String> {
    var filmBasePlatform: FilmBasePlatform?
    var movie: Movie?
    var platformMovieId: String?
}
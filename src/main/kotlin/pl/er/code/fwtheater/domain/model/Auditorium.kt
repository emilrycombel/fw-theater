package pl.er.code.fwtheater.domain.model

interface Auditorium : DomainInstance<String> {
    var capacity: Short?
    var name: String?
    var theater: Theater?
}
package pl.er.code.fwtheater.domain.model

interface Movie : DomainInstance<String> {
    var title: String?
}
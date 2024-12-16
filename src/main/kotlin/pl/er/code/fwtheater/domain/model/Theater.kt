package pl.er.code.fwtheater.domain.model


interface Theater : DomainInstance<String> {
    var name: String?
}
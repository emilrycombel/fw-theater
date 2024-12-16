package pl.er.code.fwtheater.application.port.outbound

import pl.er.code.fwtheater.domain.model.Theater

interface TheaterRepository : BaseRepository<Theater, String> {
}
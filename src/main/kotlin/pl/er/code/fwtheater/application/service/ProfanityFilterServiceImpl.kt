package pl.er.code.fwtheater.application.service

import pl.er.code.fwtheater.domain.service.ProfanityService

class ProfanityFilterServiceImpl : ProfanityService {


    override fun hasProfanedText(text: String): Boolean {
        return false
    }
}
package pl.er.code.fwtheater.domain.service

interface ProfanityService {

    fun hasProfanedText(text: String): Boolean
}
package pl.er.code.fwtheater.application.service

import com.modernmt.text.profanity.ProfanityFilter
import pl.er.code.fwtheater.domain.service.ProfanityService

class ProfanityFilterServiceImpl(private val profanityFilter: ProfanityFilter) : ProfanityService {


    override fun hasProfanedText(text: String): Boolean {
        return profanityFilter.test("pl", text) || profanityFilter.test("en", text) || profanityFilter.test("de", text)
    }
}
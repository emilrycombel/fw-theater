package pl.er.code.fwtheater.domain.usecase

import pl.er.code.fwtheater.infrastructure.common.ULIDGenerator
import pl.er.code.fwtheater.infrastructure.common.extensions.logger

abstract class BaseUseCase {
    companion object {
        val log = logger()
        val ulidGenerator: ULIDGenerator = ULIDGenerator()
    }
}
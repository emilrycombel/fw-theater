package pl.er.code.fwtheater.adapter.outbound.persistence.entity.idgenerator

import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.IdentifierGenerator
import pl.er.code.fwtheater.infrastructure.common.ULIDGenerator

class ULIDIdGenerator : IdentifierGenerator {
    companion object {
        const val NAME: String = "ulid-generator"
    }

    private val ulidGenerator: ULIDGenerator = ULIDGenerator();
    override fun generate(session: SharedSessionContractImplementor?, `object`: Any?): Any {
        return ulidGenerator.generate();
    }
}
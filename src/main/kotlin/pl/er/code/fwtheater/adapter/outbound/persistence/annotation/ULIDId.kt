package pl.er.code.fwtheater.adapter.outbound.persistence.annotation

import org.hibernate.annotations.IdGeneratorType
import pl.er.code.fwtheater.adapter.outbound.persistence.entity.idgenerator.ULIDIdGenerator

@IdGeneratorType(ULIDIdGenerator::class)
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
annotation class ULIDId()

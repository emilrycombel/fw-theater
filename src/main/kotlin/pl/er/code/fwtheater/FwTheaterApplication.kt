package pl.er.code.fwtheater

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy

@SpringBootApplication
@EnableAspectJAutoProxy
class FwTheaterApplication

fun main(args: Array<String>) {
    runApplication<FwTheaterApplication>(*args)
}

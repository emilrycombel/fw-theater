package pl.er.code.fwtheater.infrastructure.common

import com.github.f4b6a3.ulid.UlidCreator

class ULIDGenerator {
    fun generate(): String {
        return UlidCreator.getMonotonicUlid().toString();
    }
}
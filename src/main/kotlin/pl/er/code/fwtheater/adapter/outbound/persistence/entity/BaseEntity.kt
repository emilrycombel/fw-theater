package pl.er.code.fwtheater.adapter.outbound.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import java.io.Serializable
import java.time.Instant

@MappedSuperclass
abstract class BaseEntity<ID : Serializable> {

    @Id
    open var id: ID? = null

    @Column(name = "created_timestamp", nullable = false, updatable = false)
    val createdTimestamp: Instant = Instant.now()

    @Column(name = "last_updated_timestamp", nullable = false)
    var lastUpdatedTimestamp: Instant = Instant.now()

    @Column(name = "version_number", nullable = false)
    var versionNumber: Int = 0

    @Column(name = "created_by", length = 26)
    var createdBy: String? = null

    @Column(name = "last_updated_by", length = 26)
    var lastUpdatedBy: String? = null

}
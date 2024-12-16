package pl.er.code.fwtheater.adapter.outbound.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import org.hibernate.Hibernate
import java.io.Serializable
import java.time.Instant
import java.util.*

@MappedSuperclass
abstract class BaseEntity<ID : Serializable> : Persistable<ID>, Identifiable<ID> {

    @Column(name = "created_timestamp", nullable = false, updatable = false)
    open var createdTimestamp: Instant = Instant.now()

    @Column(name = "last_updated_timestamp", nullable = false)
    open var lastUpdatedTimestamp: Instant = Instant.now()

    @Column(name = "version_number", nullable = false)
    open var versionNumber: Int = 0

    @Column(name = "created_by", length = 26)
    open var createdBy: String? = null

    @Column(name = "last_updated_by", length = 26)
    open var lastUpdatedBy: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        if (other == null) {
            return false
        }

        if (!(this.javaClass.isInstance(other))) {
            return false
        }
        //it will always base of BaseEntity, might become slower due to unproxy !
        val instance: BaseEntity<*> = Hibernate.unproxy(other) as BaseEntity<*>

        return getEntityId() == instance.getEntityId()
    }

    override fun hashCode(): Int {
        return Objects.hash(getEntityId())
    }

    override fun isNew(): Boolean {
        return this.getEntityId() == null
    }

    override fun toString(): String {
        return "Entity ${this.javaClass.name}: ${getEntityId()}"
    }
}
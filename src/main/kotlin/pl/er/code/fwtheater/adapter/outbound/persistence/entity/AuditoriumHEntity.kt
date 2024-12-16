package pl.er.code.fwtheater.adapter.outbound.persistence.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import pl.er.code.fwtheater.adapter.outbound.persistence.annotation.ULIDId
import pl.er.code.fwtheater.domain.model.Auditorium
import pl.er.code.fwtheater.domain.model.Theater

@Entity
@Table(name = "ff_auditoriums")
open class AuditoriumHEntity : BaseEntity<String>(), Auditorium {
    @Id
    @Size(max = 26)
    @ULIDId
    @Column(name = "ff_auditorium", nullable = false, length = 26)
    override var id: String? = null

    @NotNull
    @Column(name = "capacity", nullable = false)
    override var capacity: Short? = null

    @Size(max = 250)
    @NotNull
    @Column(name = "name", nullable = false, length = 250)
    override var name: String? = null

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = TheaterHEntity::class)
    @JoinColumn(name = "ff_theater_id", nullable = false)
    override var theater: Theater? = null

    override fun getEntityId(): String? {
        return id
    }
}
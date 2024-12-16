package pl.er.code.fwtheater.adapter.outbound.persistence.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import pl.er.code.fwtheater.adapter.outbound.persistence.annotation.ULIDId

@Entity
@Table(name = "ff_auditoriums")
open class AuditoriumHEntity : BaseEntity<String>() {
    @Id
    @Size(max = 26)
    @ULIDId
    @Column(name = "ff_auditorium", nullable = false, length = 26)
    override var id: String? = null

    @NotNull
    @Column(name = "capacity", nullable = false)
    open var capacity: Short? = null

    @Size(max = 250)
    @NotNull
    @Column(name = "name", nullable = false, length = 250)
    open var name: String? = null

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ff_theater_id", nullable = false)
    open var theater: pl.er.code.fwtheater.adapter.outbound.persistence.entity.TheaterHEntity? = null
}
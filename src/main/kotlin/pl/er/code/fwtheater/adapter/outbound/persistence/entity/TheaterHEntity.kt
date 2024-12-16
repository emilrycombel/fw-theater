package pl.er.code.fwtheater.adapter.outbound.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
@Table(name = "ff_theaters")
open class TheaterHEntity : BaseEntity<String>() {
    @Id
    @Size(max = 26)
    @Column(name = "ff_theater_id", nullable = false, length = 26)
    override var id: String? = null

    @Size(max = 250)
    @NotNull
    @Column(name = "name", nullable = false, length = 250)
    open var name: String? = null
}